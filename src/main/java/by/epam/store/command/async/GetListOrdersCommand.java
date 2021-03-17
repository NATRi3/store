package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Order;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.OrderService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.ResponseWriterUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetListOrdersCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(GetListOrdersCommand.class);
    private static final OrderService BASE_ORDER_SERVICE = ServiceCreator.getInstance().getOrderService();

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
