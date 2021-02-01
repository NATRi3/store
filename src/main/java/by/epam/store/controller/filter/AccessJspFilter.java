package by.epam.store.controller.filter;

import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

public class AccessJspFilter implements Filter {
    private static Map<TypeRole,String[]> jspRoleAccess;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        jspRoleAccess = JspAccessMap.getINSTANCE();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestUri = request.getRequestURI().replace(request.getContextPath(),"");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        boolean access = false;
        for(Map.Entry<TypeRole,String[]> entry:jspRoleAccess.entrySet()){
            if(user.getRole().equals(entry.getKey())) {
                for (String uri : entry.getValue()) {
                    if (requestUri.contains(uri)) {
                        session.setAttribute(SessionAttribute.PAGE,uri);
                        access = true;
                        break;
                    }
                }
            }
        }
        if(access){
            filterChain.doFilter(servletRequest,servletResponse);
        }else {
            System.out.println("wrong access "+requestUri +"----"+ user.getRole());
            request.setAttribute(RequestParameter.MESSAGE, MessageErrorKey.ERROR_MESSAGE_WRONG_ACCESS);
            RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.MAIN_PAGE);
            dispatcher.forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
