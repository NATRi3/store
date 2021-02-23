package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Order;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.OrderService;
import by.epam.store.util.RequestParameter;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetListOrdersCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(GetListOrdersCommand.class);
    private static final OrderService orderService = ServiceCreator.getInstance().getOrderService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String status = request.getParameter(RequestParameter.TYPE_STATUS);
            String sort = request.getParameter(RequestParameter.TYPE_SORT);
            String begin = request.getParameter(RequestParameter.BEGIN_PAGINATION);
            try {
                List<Order> orderList = orderService.getOrderList(begin, sort, status);
                String json = new Gson().toJson(orderList);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (ServiceException e) {
                log.error(e);
                response.sendError(500);
            }
        } catch (IOException e){
            log.error(e);
        }
    }
}
