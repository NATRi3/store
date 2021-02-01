package by.epam.store.controller.filter;

import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@Log4j2
public class AccessCommandFilter implements Filter {
    private static Map<TypeRole,String[]> commandRoleAccess;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        commandRoleAccess = CommandAccessMap.getINSTANCE();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String requestCommand;
        requestCommand = request.getParameter(RequestParameter.COMMAND);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        boolean access = false;
        for(Map.Entry<TypeRole,String[]> entry:commandRoleAccess.entrySet()){
            if (user.getRole().equals(entry.getKey())) {
                for (String command : entry.getValue()) {
                    if (requestCommand==null||requestCommand.contains(command)) {
                        access = true;
                        break;
                    }
                }
            }
        }
        if(access){
            filterChain.doFilter(servletRequest,servletResponse);
        } else {
            log.info("wrong access "+requestCommand + user.toString());
            request.setAttribute(RequestParameter.MESSAGE, MessageErrorKey.ERROR_MESSAGE_WRONG_ACCESS);
            RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.MAIN_PAGE);
            dispatcher.forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
