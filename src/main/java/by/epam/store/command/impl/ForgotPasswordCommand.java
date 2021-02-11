package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
@Log4j2
public class ForgotPasswordCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String email = request.getParameter(RequestParameter.EMAIL);
        try {
            userService.changePasswordSendForgotMailMessage(email);
            request.setAttribute(RequestParameter.MESSAGE, MessageErrorKey.SUCCESSFUL_CHANGE_PASSWORD);
            page = PagePath.LOGIN;
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE, e.getMessage());
            page = PagePath.FORGOT_PASSWORD;
        }
        return page;
    }
}
