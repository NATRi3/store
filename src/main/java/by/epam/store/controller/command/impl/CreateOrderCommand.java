package by.epam.store.controller.command.impl;

import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import by.epam.store.controller.Router;
import by.epam.store.model.entity.Cart;
import by.epam.store.model.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

/**
 * The Create order command.
 */
public class CreateOrderCommand implements Command {
    private final static Logger log = LogManager.getLogger(CreateOrderCommand.class);
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String[]> parameters =
                RequestUtil.getAllParametersFrom(request,
                        Set.of(RequestParameterAndAttribute.PHONE, RequestParameterAndAttribute.ADDRESS));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        try {
            String message = orderService.createOrder(parameters, user, cart);
            page = RouterResponseHelper.router(request, message, PagePath.CART);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
