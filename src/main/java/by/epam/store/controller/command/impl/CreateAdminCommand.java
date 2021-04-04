package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

/**
 * The Create admin command.
 */
public class CreateAdminCommand implements Command {
    private static final Logger logger = LogManager.getLogger(CreateAdminCommand.class);
    private UserService baseUserService;

    @Autowired
    public void setBaseUserService(UserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String[]> parameters = RequestUtil.getAllParametersFrom(request);
        try {
            Optional<String> optionalMessage = baseUserService.registerAdmin(parameters);
            page = RouterResponseHelper.router(request, optionalMessage,
                    PagePath.ADMIN_PANEL_USERS, PagePath.ADMIN_PANEL_USERS);
        } catch (ServiceException e) {
            logger.info(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
