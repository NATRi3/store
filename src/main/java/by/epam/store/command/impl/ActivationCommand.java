package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ServiceCreator;
import by.epam.store.service.UserService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.RouterResponseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class ActivationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ActivationCommand.class);
    private static final UserService BASE_USER_SERVICE = ServiceCreator.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        String code = request.getParameter(RequestParameterAndAttribute.ACTIVATION_CODE);
        try {
            String message = BASE_USER_SERVICE.activate(code);
            page = RouterResponseHelper.router(request, message, PagePath.LOGIN);
        } catch (ServiceException e) {
            logger.info(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
