package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Order;
import by.epam.store.entity.User;
import by.epam.store.exception.CommandException;
import by.epam.store.service.impl.OrderService;
import by.epam.store.util.ResponseWriterUtil;
import by.epam.store.util.SessionAttribute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetUserOrdersCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(GetUserOrdersCommand.class);
    private static final OrderService orderService = ServiceCreator.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            List<Order> orderList = orderService.findUserOrders(user.getId());
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(orderList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (Throwable e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
