package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.PagePath;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        HttpSession session = request.getSession();
        try {
            User user = userService.login(email,password);
            session.setAttribute(SessionAttribute.USER, user);
            return PagePath.MAIN_PAGE;
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            request.setAttribute(RequestParameter.MESSAGE, e.getMessage());
            return PagePath.LOGIN_PAGE;
        }
    }
}
