package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.MessageCreator;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.ResponseWriterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ChangeUserStatusCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(ChangeUserStatusCommand.class);
    private final static UserService userService = ServiceCreator.getInstance().getUserService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String id = request.getParameter(RequestParameter.ID_USER);
        String statusFrom = request.getParameter(RequestParameter.TYPE_STATUS);
        String statusTo = request.getParameter(RequestParameter.TYPE_STATUS);
        try {
            String messageKey = userService.changeStatusFromTo(id,statusFrom, statusTo);
            ResponseWriterUtil.writeTextToResponse(request,response,messageKey);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
