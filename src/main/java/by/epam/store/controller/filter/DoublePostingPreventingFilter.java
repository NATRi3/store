package by.epam.store.controller.filter;

import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

public class DoublePostingPreventingFilter implements Filter {
    private final static Logger log = LogManager.getLogger(DoublePostingPreventingFilter.class);

    @Override
    public void init(FilterConfig fg) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpSession session;
        if (request.getMethod().equals("GET")) {
            session = request.getSession(true);
            session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
            chain.doFilter(req, res);
        } else {
            session = request.getSession();
            int serverToken = (Integer) session.getAttribute(SessionAttribute.SERVER_TOKEN);
            if (req.getParameter(RequestParameterAndAttribute.CLIENT_TOKEN) != null &&
                    serverToken == Integer.parseInt(req.getParameter(RequestParameterAndAttribute.CLIENT_TOKEN))) {
                session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
                chain.doFilter(req, res);
            } else {
                log.info("Tokens: " + serverToken + "-" + req.getParameter(RequestParameterAndAttribute.CLIENT_TOKEN));
                session.setAttribute(SessionAttribute.SERVER_TOKEN, new Random().nextInt(10000));
                HttpServletResponse response = (HttpServletResponse) res;
                response.sendRedirect(request.getContextPath() + session.getAttribute(SessionAttribute.PAGE));
            }
        }
    }

    @Override
    public void destroy() {
    }
}
