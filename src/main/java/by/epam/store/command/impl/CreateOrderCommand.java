package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Cart;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.OrderService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class CreateOrderCommand implements Command {
    private final static Logger log = LogManager.getLogger(CreateOrderCommand.class);
    private static final OrderService orderService = ServiceCreator.getInstance().getOrderService();
    @Override
    public String execute(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameter.PHONE,request.getParameter(RequestParameter.PHONE));
        parameters.put(RequestParameter.ADDRESS,request.getParameter(RequestParameter.ADDRESS));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        try{
            String message = orderService.createOrder(parameters, user, cart);
            request.setAttribute(RequestParameter.MESSAGE,message);
            for(Map.Entry<String,String> entry: parameters.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
            return PagePath.CART;
        } catch (ServiceException e) {
            log.error(e);
            return PagePath.PAGE_500;
        }
    }
}
