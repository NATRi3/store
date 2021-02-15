package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log4j2
public class ForgotPasswordCommand implements Command {
    private static final UserService userService = ServiceCreator.getInstance().getUserService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String email = request.getParameter(RequestParameter.EMAIL);
        try {
            Optional<String> optional = userService.changePasswordSendForgotMailMessage(email);
            if(optional.isPresent()){
                request.setAttribute(RequestParameter.MESSAGE,optional.get());
                page = PagePath.FORGOT_PASSWORD;
            } else {
                page = PagePath.LOGIN;
            }
        } catch (ServiceException e) {
            log.error(e);
            page = PagePath.PAGE_500;
        }
        return page;
    }
}
