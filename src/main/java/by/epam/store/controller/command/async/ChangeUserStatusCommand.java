package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Change user status command.
 */
public class ChangeUserStatusCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(ChangeUserStatusCommand.class);
    private UserService BASE_USER_SERVICE;

    @Autowired
    public void setBASE_USER_SERVICE(UserService BASE_USER_SERVICE) {
        this.BASE_USER_SERVICE = BASE_USER_SERVICE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String id = request.getParameter(RequestParameterAndAttribute.ID_USER);
        String statusFrom = request.getParameterValues(RequestParameterAndAttribute.TYPE_STATUS)[0];
        String statusTo = request.getParameterValues(RequestParameterAndAttribute.TYPE_STATUS)[1];
        try {
            String messageKey = BASE_USER_SERVICE.changeStatusFromTo(id, statusFrom, statusTo);
            ResponseWriterUtil.writeTextToResponse(request, response, messageKey);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
