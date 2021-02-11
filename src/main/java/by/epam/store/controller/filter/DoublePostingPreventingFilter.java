package by.epam.store.controller.filter;

import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;
@Log4j2
public class DoublePostingPreventingFilter implements Filter {
    @Override
    public void init(FilterConfig fg) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session;
        if(request.getMethod().equals("GET")){
            session = request.getSession(true);
            session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
            chain.doFilter(req, res);
        }else{
            session = request.getSession();
            int serverToken = (Integer)session.getAttribute(SessionAttribute.SERVER_TOKEN);
            if(req.getParameter(RequestParameter.CLIENT_TOKEN)!=null&&
            serverToken == Integer.parseInt(req.getParameter(RequestParameter.CLIENT_TOKEN))){
                session.setAttribute(SessionAttribute.SERVER_TOKEN,  new Random().nextInt(10000));
                chain.doFilter(req, res);
            } else {
                log.info("Tokens: "+serverToken+"-"+req.getParameter(RequestParameter.CLIENT_TOKEN));
                session.setAttribute(SessionAttribute.SERVER_TOKEN,  new Random().nextInt(10000));
                HttpServletResponse response = (HttpServletResponse) res;
                response.sendRedirect(request.getContextPath()+session.getAttribute(SessionAttribute.PAGE));
            }
        }
    }
    @Override
    public void destroy() {
    }
}
