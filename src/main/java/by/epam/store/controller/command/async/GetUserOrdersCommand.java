package by.epam.store.controller.command.async;

import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.CommandAsync;
import by.epam.store.model.entity.Order;
import by.epam.store.model.entity.User;
import by.epam.store.exception.CommandException;
import by.epam.store.model.service.OrderService;
import by.epam.store.controller.command.SessionAttribute;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * The Get user orders command.
 */
public class GetUserOrdersCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(GetUserOrdersCommand.class);
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

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
