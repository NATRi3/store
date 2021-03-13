package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.entity.Cart;
import by.epam.store.util.MessageKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.SessionAttribute;
import by.epam.store.validator.NumberValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class RemoveProductFromCartCommand implements Command {
    private final static Logger log = LogManager.getLogger(RemoveProductFromCartCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        String idStr = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        if (NumberValidator.isLongValid(idStr)) {
            long id = Long.parseLong(idStr);
            cart.deleteProduct(id);
            return Router.redirectTo(PagePath.CART, request);
        } else {
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_UNKNOWN_PRODUCT);
            return Router.forwardTo(PagePath.CART);
        }
    }
}
