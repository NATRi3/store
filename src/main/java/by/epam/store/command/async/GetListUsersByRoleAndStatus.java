package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.User;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.ResponseWriterUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetListUsersByRoleAndStatus implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListUsersByRoleAndStatus.class);
    private static final UserService userService = ServiceCreator.getInstance().getUserService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String begin = request.getParameter(RequestParameterAndAttribute.BEGIN_PAGINATION);
        String status = request.getParameter(RequestParameterAndAttribute.TYPE_STATUS);
        try {
            List<User> userList = userService.findUsersByRoleAndStatus(status, begin);
            String json = new Gson().toJson(userList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
