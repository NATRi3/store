package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ServiceCreator;
import by.epam.store.service.UserService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.RouterResponseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;


public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private static final UserService BASE_USER_SERVICE = ServiceCreator.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String> parameters = RequestUtil.getAllParametersFrom(request);
        try {
            Optional<String> optionalMessage = BASE_USER_SERVICE.registerClient(parameters);
            page = RouterResponseHelper
                    .router(request, optionalMessage, parameters,
                            PagePath.REGISTRATION, PagePath.LOGIN);
        } catch (ServiceException e) {
            logger.info(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
