package by.epam.store.controller.filter;

import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import by.epam.store.model.entity.TypeRole;
import by.epam.store.model.entity.User;
import by.epam.store.util.MessageKey;
import by.epam.store.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * The filter for check Access to command and jsp.
 */
public class AccessCommandAndJspFilter implements Filter {
    private static final Logger log = LogManager.getLogger(AccessCommandAndJspFilter.class);
    private static final String CONTROLLER_PREFIX = "/controller";
    private static Map<TypeRole, Set<String>> commandRoleAccess;
    private static Map<TypeRole, Set<String>> jspRoleAccess;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandRoleAccess = CommandAccessMap.getInstance();
        jspRoleAccess = JspAccessMap.getINSTANCE();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestPath;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Result result;
        if (request.getRequestURI().contains(CONTROLLER_PREFIX)) {
            requestPath = request.getParameter(RequestParameterAndAttribute.COMMAND);
            result = checkMapForPathAndUserRole(user, commandRoleAccess, requestPath);
        } else {
            requestPath = request.getRequestURI().replace(request.getContextPath(), "");
            result = checkMapForPathAndUserRole(user, jspRoleAccess, requestPath);
        }
        if (result.isAccess()) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            log.info("Wrong access " + requestPath + user.getRole());
            RequestDispatcher dispatcher;
            if (!result.isExists()) {
                HttpServletResponse res = (HttpServletResponse) servletResponse;
                res.sendRedirect(request.getContextPath()+PagePath.PAGE_404);
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

    private Result checkMapForPathAndUserRole(User user, Map<TypeRole, Set<String>> map, String path) {
        Result.ResultBuilder builder = Result.builder();
        for (Map.Entry<TypeRole, Set<String>> entry : map.entrySet()) {
            if (entry.getValue().contains(path)) {
                if (user.getRole().equals(entry.getKey())) {
                    builder.access(true);
                }
                builder.isExists(true);
            }
        }
        return builder.build();
    }
    private static class Result {
        private boolean isExists;
        private boolean access;

        public Result(boolean isExists, boolean access) {
            this.isExists = isExists;
            this.access = access;
        }

        public static ResultBuilder builder() {
            return new ResultBuilder();
        }

        private void setExists(boolean exists) {
            isExists = exists;
        }

        private void setAccess(boolean access) {
            this.access = access;
        }

        private boolean isExists() {
            return isExists;
        }

        private boolean isAccess() {
            return access;
        }

        public static class ResultBuilder {
            private boolean isExists = false;
            private boolean access = false;

            ResultBuilder() {
            }

            public ResultBuilder isExists(boolean isExists) {
                this.isExists = isExists;
                return this;
            }

            public ResultBuilder access(boolean access) {
                this.access = access;
                return this;
            }

            public Result build() {
                return new Result(isExists, access);
            }

            public String toString() {
                return "AccessCommandAndJspFilter.Result.ResultBuilder(isExists=" + this.isExists + ", access=" + this.access + ")";
            }
        }
    }

    @Override
    public void destroy() {

    }
}
