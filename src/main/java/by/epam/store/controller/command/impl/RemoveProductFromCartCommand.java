package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.model.entity.Cart;
import by.epam.store.util.MessageKey;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import by.epam.store.model.validator.NumberValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The Remove product from cart command.
 */
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
