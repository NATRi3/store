package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(RequestParameter.NAME);
        String email = request.getParameter(RequestParameter.EMAIL);
        String password = request.getParameter(RequestParameter.PASSWORD);
        String repeatPassword = request.getParameter(RequestParameter.REPEAT_PASSWORD);
        String description = request.getParameter("description");
        String data = request.getParameter("data");
        System.out.println(name);
        try {
            User newUser = userService.register(name,email,password,repeatPassword);
            request.setAttribute(RequestParameter.MESSAGE, MessageErrorKey.ERROR_MESSAGE_SUCCESSFUL_REGISTRATION);
            logger.info(newUser.getEmail()+" "+MessageErrorKey.ERROR_MESSAGE_SUCCESSFUL_REGISTRATION);
            return PagePath.LOGIN_PAGE;
        } catch (ServiceException e) {
            logger.info(e.getMessage());
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
            return PagePath.REGISTRATION_PAGE;
        }
    }
}
