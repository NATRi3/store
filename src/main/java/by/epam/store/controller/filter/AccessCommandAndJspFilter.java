package by.epam.store.controller.filter;

import by.epam.store.entity.TypeRole;
import by.epam.store.entity.User;
import by.epam.store.util.MessageKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class AccessCommandAndJspFilter implements Filter {
    private final static Logger log = LogManager.getLogger(AccessCommandAndJspFilter.class);
    public static final String CONTROLLER_PREFIX = "/controller";
    private static Map<TypeRole, Set<String>> commandRoleAccess;
    private static Map<TypeRole, Set<String>> jspRoleAccess;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandRoleAccess = CommandAccessMap.getINSTANCE();
        jspRoleAccess = JspAccessMap.getINSTANCE();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestPath;
        if (request.getRequestURI().contains(CONTROLLER_PREFIX)) {
            requestPath = request.getParameter(RequestParameterAndAttribute.COMMAND);
        } else {
            requestPath = request.getRequestURI().replace(request.getContextPath(),"");
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Result result = new Result();
        checkMapForPathAndUserRole(user,result,commandRoleAccess,requestPath);
        checkMapForPathAndUserRole(user,result,jspRoleAccess,requestPath);
        if (result.isAccess()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.info("Wrong access " + requestPath + user.getRole());
            RequestDispatcher dispatcher;
            if(!result.isExists()) {
                HttpServletResponse res = (HttpServletResponse) servletResponse;
                res.sendRedirect(PagePath.PAGE_404);
                return;
            } else if (user.getRole().equals(TypeRole.GUEST)) {
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_MESSAGE_LOGIN_PLEASE);
                dispatcher = request.getRequestDispatcher(PagePath.LOGIN);
            } else {
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_MESSAGE_WRONG_ACCESS);
                dispatcher = request.getRequestDispatcher(PagePath.MAIN);
            }
            dispatcher.forward(servletRequest, servletResponse);
        }
    }

    private void checkMapForPathAndUserRole(User user, Result result, Map<TypeRole, Set<String>> map, String path){
        for (Map.Entry<TypeRole, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().contains(path)) {
                if (user.getRole().equals(entry.getKey())) {
                    result.setAccess(true);
                }
                result.setExists(true);
            }
        }
    }

    private static class Result{
        private boolean isExists;
        private boolean access;

        Result(boolean access, boolean isExists){
            this.isExists = isExists;
            this.access = access;
        }

        public Result() {
        }

        public void setExists(boolean exists) {
            isExists = exists;
        }

        public void setAccess(boolean access) {
            this.access = access;
        }

        public boolean isExists() {
            return isExists;
        }

        public boolean isAccess() {
            return access;
        }
    }

    @Override
    public void destroy() {

    }
}
