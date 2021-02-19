package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Order;
import by.epam.store.entity.Product;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.OrderService;
import by.epam.store.util.SessionAttribute;
import com.google.gson.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetUserOrdersCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(GetUserOrdersCommand.class);
    private static final OrderService orderService = ServiceCreator.getInstance().getOrderService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(SessionAttribute.USER);
            try {
                List<Order> orderList = orderService.getUserOrders(user.getId());
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String json = gson.toJson(orderList);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (Throwable e) {
                log.error(e);
                response.sendError(500);
            }
        }catch (IOException e){
            log.error(e);
        }
    }
}
