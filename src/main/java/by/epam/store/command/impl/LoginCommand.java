package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger logger = LogManager.getLogger(LoginCommand.class);
    private static final UserService userService = ServiceCreator.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        String email = request.getParameter(RequestParameterAndAttribute.EMAIL);
        String password = request.getParameter(RequestParameterAndAttribute.PASSWORD);
        User user = new User(email);
        HttpSession session = request.getSession();
        try {
            Optional<String> optionalMessage = userService.login(user, password);
            if (!optionalMessage.isPresent()) {
                session.setAttribute(SessionAttribute.USER, user);
                return Router.redirectTo(PagePath.MAIN, request);
            } else {
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, optionalMessage.get());
                return Router.forwardTo(PagePath.LOGIN, request);
            }
        } catch (ServiceException e) {
            logger.info(e);
            return Router.redirectTo(PagePath.PAGE_500, request);
        }
    }
}
