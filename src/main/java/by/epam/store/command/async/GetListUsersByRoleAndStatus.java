package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.User;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ServiceCreator;
import by.epam.store.service.UserService;
import by.epam.store.command.RequestParameterAndAttribute;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The Get list users by role and status.
 */
public class GetListUsersByRoleAndStatus implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListUsersByRoleAndStatus.class);
    private static final UserService BASE_USER_SERVICE = ServiceCreator.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String begin = request.getParameter(RequestParameterAndAttribute.BEGIN_PAGINATION);
        String status = request.getParameter(RequestParameterAndAttribute.TYPE_STATUS);
        try {
            List<User> userList = BASE_USER_SERVICE.findUsersByRoleAndStatus(status, begin);
            String json = new Gson().toJson(userList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
