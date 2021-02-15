package by.epam.store.controller.filter;

import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
@Log4j2
public class AccessAsyncCommandFilter implements Filter {
    private static Map<TypeRole, Set<String>> asyncCommandRoleAccess;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        asyncCommandRoleAccess = AsyncCommandAccessMap.getINSTANCE();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestCommand;
        requestCommand = request.getParameter(RequestParameter.COMMAND);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        for(Map.Entry<TypeRole,Set<String>> entry:asyncCommandRoleAccess.entrySet()){
            if (user.getRole().equals(entry.getKey())) {
                if (entry.getValue().contains(requestCommand)) {
                    filterChain.doFilter(servletRequest,servletResponse);
                } else {
                    log.info("Wrong access " + requestCommand + user.getRole());
                    HttpServletResponse response = (HttpServletResponse) servletResponse;
                    if(user.getRole().equals(TypeRole.GUEST)){
                        response.sendError(402);
                    } else {
                        response.sendError(403);
                    }
                }
            }
        }
    }

    @Override
    public void destroy() {

    }
}