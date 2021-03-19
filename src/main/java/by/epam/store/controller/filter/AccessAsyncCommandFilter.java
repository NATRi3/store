package by.epam.store.controller.filter;

import by.epam.store.entity.TypeRole;
import by.epam.store.entity.User;
import by.epam.store.util.MessageCreator;
import by.epam.store.util.MessageKey;
import by.epam.store.command.RequestParameterAndAttribute;
import by.epam.store.command.SessionAttribute;
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
 * The filter for check Access to async command.
 */
public class AccessAsyncCommandFilter implements Filter {
    private final static Logger log = LogManager.getLogger(AccessAsyncCommandFilter.class);
    private static Map<TypeRole, Set<String>> asyncCommandRoleAccess;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        asyncCommandRoleAccess = AsyncCommandAccessMap.getINSTANCE();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestCommand;
        requestCommand = request.getParameter(RequestParameterAndAttribute.COMMAND);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        for (Map.Entry<TypeRole, Set<String>> entry : asyncCommandRoleAccess.entrySet()) {
            if (user.getRole().equals(entry.getKey())) {
                if (entry.getValue().contains(requestCommand)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    log.info("Wrong access " + requestCommand + user.getRole());
                    HttpServletResponse response = (HttpServletResponse) servletResponse;
                    if (user.getRole().equals(TypeRole.GUEST)) {
                        response.sendError(402);
                    } else {
                        if (response.getContentType().contains("text")) {
                            String message = MessageCreator.instance.getMessageFromBundleByLocale(MessageKey.ERROR_MESSAGE_WRONG_ACCESS, request);
                            response.setContentType("application/text");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write(message);
                        } else {
                            response.sendError(403);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}
