package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.RouterResponseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreateAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateAdminCommand.class);
    private final UserService userService = ServiceCreator.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String,String> parameters = RequestUtil.getAllParametersFrom(request);
        try {
            Optional<String> optionalMessage = userService.registerAdmin(parameters);
            page = RouterResponseHelper.router(request, optionalMessage,
                    PagePath.ADMIN_PANEL_USERS, PagePath.ADMIN_PANEL_USERS);
        } catch (ServiceException e) {
            logger.info(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
