package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ForgotPasswordCommand implements Command {
    private final static Logger log = LogManager.getLogger(ForgotPasswordCommand.class);
    private static final UserService userService = ServiceCreator.getInstance().getUserService();
    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        String email = request.getParameter(RequestParameter.EMAIL);
        try {
            Optional<String> optional = userService.changePasswordSendForgotMailMessage(email);
            if(optional.isPresent()){
                request.setAttribute(RequestParameter.MESSAGE,optional.get());
                page = Router.forwardTo(PagePath.FORGOT_PASSWORD,request);
            } else {
                page = Router.redirectTo(PagePath.LOGIN,request);
            }
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500,request);
        }
        return page;
    }
}
