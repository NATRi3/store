package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ServiceCreator;
import by.epam.store.service.UserService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ForgotPasswordCommand implements Command {
    private final static Logger log = LogManager.getLogger(ForgotPasswordCommand.class);
    private static final UserService BASE_USER_SERVICE = ServiceCreator.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        String email = request.getParameter(RequestParameterAndAttribute.EMAIL);
        try {
            Optional<String> optional = BASE_USER_SERVICE.changePasswordSendForgotMailMessage(email);
            if (optional.isPresent()) {
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, optional.get());
                page = Router.forwardTo(PagePath.FORGOT_PASSWORD);
            } else {
                page = Router.redirectTo(PagePath.LOGIN, request);
            }
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
