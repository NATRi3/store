package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import by.epam.store.controller.Router;
import by.epam.store.model.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The Change password command.
 */
public class ChangePasswordCommand implements Command {
    private static final Logger log = LogManager.getLogger(ChangePasswordCommand.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String userId = String.valueOf(((User) request.getSession().getAttribute(SessionAttribute.USER)).getId());
        String oldPassword = request.getParameter(RequestParameterAndAttribute.CHANGE_PASSWORD_OLD);
        String newPassword = request.getParameter(RequestParameterAndAttribute.PASSWORD);
        String newPasswordRepeat = request.getParameter(RequestParameterAndAttribute.REPEAT_PASSWORD);
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put(RequestParameterAndAttribute.CHANGE_PASSWORD_OLD, new String[]{oldPassword});
        parameters.put(RequestParameterAndAttribute.PASSWORD, new String[]{newPassword, newPasswordRepeat});
        parameters.put(RequestParameterAndAttribute.ID_USER, new String[]{userId});
        try {
            userService.changePassword(parameters);
            return Router.forwardTo(PagePath.ACCOUNT);
        } catch (ServiceException e) {
            log.error(e);
            return Router.redirectTo(PagePath.PAGE_500, request);
        }
    }
}
