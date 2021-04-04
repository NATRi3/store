package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

/**
 * The Activation user by id command.
 */
public class ActivationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ActivationCommand.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        String code = request.getParameter(RequestParameterAndAttribute.ACTIVATION_CODE);
        try {
            String message = userService.activate(code);
            page = RouterResponseHelper.router(request, message, PagePath.LOGIN);
        } catch (ServiceException e) {
            logger.info(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
