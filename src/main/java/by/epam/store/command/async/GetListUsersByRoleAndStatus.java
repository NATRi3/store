package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.RequestParameter;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class GetListUsersByRoleAndStatus implements CommandAsync {
    private static final UserService userService = ServiceCreator.getInstance().getUserService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String begin = request.getParameter(RequestParameter.BEGIN_PAGINATION);
            String role = request.getParameter(RequestParameter.TYPE_ROLE);
            String status = request.getParameter(RequestParameter.TYPE_STATUS);
            try {
                List<User> userList = userService.findUsersByRoleAndStatus(role, status, begin);
                String json = new Gson().toJson(userList);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (ServiceException e) {
                log.error(e);
                response.sendError(500);
            }
        } catch (IOException ex) {
            log.error(ex);
        }
    }
}
