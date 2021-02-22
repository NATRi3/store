package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActivationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ActivationCommand.class);
    private static final UserService userService = ServiceCreator.getInstance().getUserService();
    @Override
    public Router execute(HttpServletRequest request) {
        String code = request.getParameter(RequestParameter.ACTIVATION_CODE);
        try {
            String message = userService.activate(code);
            request.setAttribute(RequestParameter.MESSAGE, message);
            return Router.forwardTo(PagePath.LOGIN);
        } catch (ServiceException e) {
            logger.info(e);
            return Router.redirectTo(PagePath.PAGE_500);
        }
    }
}
