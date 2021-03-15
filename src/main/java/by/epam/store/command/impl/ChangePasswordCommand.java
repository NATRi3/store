package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ServiceCreator;
import by.epam.store.service.UserService;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class ChangePasswordCommand implements Command {
    private static final Logger log = LogManager.getLogger(ChangePasswordCommand.class);
    private static final UserService userService = ServiceCreator.getInstance().getUserService();

    @Override
    public Router execute(HttpServletRequest request) {
        String userId = String.valueOf(((User) request.getSession().getAttribute(SessionAttribute.USER)).getId());
        String oldPassword = request.getParameter(RequestParameterAndAttribute.CHANGE_PASSWORD_OLD);
        String newPassword = request.getParameter(RequestParameterAndAttribute.PASSWORD);
        String newPasswordRepeat = request.getParameter(RequestParameterAndAttribute.REPEAT_PASSWORD);
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameterAndAttribute.CHANGE_PASSWORD_OLD, oldPassword);
        parameters.put(RequestParameterAndAttribute.PASSWORD,newPassword);
        parameters.put(RequestParameterAndAttribute.REPEAT_PASSWORD,newPasswordRepeat);
        parameters.put(RequestParameterAndAttribute.ID_USER,userId);
        try {
            userService.changePassword(parameters);
        } catch (ServiceException e) {
            e.printStackTrace();
        }
        return null;
    }
}
