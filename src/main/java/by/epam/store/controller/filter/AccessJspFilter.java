package by.epam.store.controller.filter;

import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.util.MessageKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
@Log4j2
public class AccessJspFilter implements Filter {
    private static Map<TypeRole,Set<String>> jspRoleAccess;
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
        for(Map.Entry<TypeRole, Set<String>> entry:jspRoleAccess.entrySet()){
            if(user.getRole().equals(entry.getKey())) {
                if (entry.getValue().contains(requestUri)) {
                    session.setAttribute(SessionAttribute.PAGE,requestUri);
                    filterChain.doFilter(servletRequest,servletResponse);
                }else {
                    log.info("Wrong access " + requestUri + user.getRole());
                    RequestDispatcher dispatcher;
                    if(user.getRole().equals(TypeRole.GUEST)) {
                        request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_MESSAGE_LOGIN_PLEASE);
                        dispatcher = request.getRequestDispatcher(PagePath.LOGIN);
                    } else {
                        request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_MESSAGE_WRONG_ACCESS);
                        dispatcher = request.getRequestDispatcher(PagePath.MAIN);
                    }
                    dispatcher.forward(servletRequest, servletResponse);
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
