package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ServiceCreator;
import by.epam.store.service.UserService;
import by.epam.store.util.PagePath;
import by.epam.store.command.RequestParameterAndAttribute;
import by.epam.store.command.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * The Login command.
 */
public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final UserService BASE_USER_SERVICE = ServiceCreator.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        String email = request.getParameter(RequestParameterAndAttribute.EMAIL);
        String password = request.getParameter(RequestParameterAndAttribute.PASSWORD);
        User user = new User(email);
        HttpSession session = request.getSession();
        try {
            Optional<String> optionalMessage = BASE_USER_SERVICE.login(user, password);
            if (!optionalMessage.isPresent()) {
                session.setAttribute(SessionAttribute.USER, user);
                return Router.redirectTo(PagePath.MAIN, request);
            } else {
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, optionalMessage.get());
                return Router.forwardTo(PagePath.LOGIN);
            }
        } catch (ServiceException e) {
            logger.info(e);
            return Router.redirectTo(PagePath.PAGE_500, request);
        }
    }
}
