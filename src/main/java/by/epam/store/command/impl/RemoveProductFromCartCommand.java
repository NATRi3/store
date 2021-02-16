package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.entity.Cart;
import by.epam.store.util.MessageKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveProductFromCartCommand implements Command {
    private final static Logger log = LogManager.getLogger(RemoveProductFromCartCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String idStr = request.getParameter(RequestParameter.ID_PRODUCT);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        try{
            long id = Long.parseLong(idStr);
            cart.deleteProduct(id);
        } catch (NumberFormatException e){
            log.warn(e);
            request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_UNKNOWN_PRODUCT);
        }
        return PagePath.CART;
    }
}
