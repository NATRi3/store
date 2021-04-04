package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.model.entity.Order;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.OrderService;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.annotation.Autowired;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The Get list orders command.
 */
public class GetListOrdersCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(GetListOrdersCommand.class);
    private OrderService BASE_ORDER_SERVICE;

    @Autowired
    public void setBASE_ORDER_SERVICE(OrderService BASE_ORDER_SERVICE) {
        this.BASE_ORDER_SERVICE = BASE_ORDER_SERVICE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String sort = request.getParameter(RequestParameterAndAttribute.TYPE_SORT);
        String begin = request.getParameter(RequestParameterAndAttribute.BEGIN_PAGINATION);
        try {
            List<Order> orderList = BASE_ORDER_SERVICE.findOrderList(begin, sort);
            String json = new Gson().toJson(orderList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
