package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The Forgot password command.
 */
public class ForgotPasswordCommand implements Command {
    private final static Logger log = LogManager.getLogger(ForgotPasswordCommand.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        String email = request.getParameter(RequestParameterAndAttribute.EMAIL);
        try {
            Optional<String> optional = userService.changePasswordSendForgotMailMessage(email);
            page = RouterResponseHelper.router(request, optional, PagePath.FORGOT_PASSWORD, PagePath.LOGIN);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
