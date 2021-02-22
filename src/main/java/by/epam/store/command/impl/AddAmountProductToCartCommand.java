package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.entity.Cart;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import by.epam.store.validator.NumberValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class AddAmountProductToCartCommand implements Command {
    private final static Logger log = LogManager.getLogger(AddAmountProductToCartCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public Router execute(HttpServletRequest request) {
        String idStr = request.getParameter(RequestParameter.ID_PRODUCT);
        String amount = request.getParameter(RequestParameter.PRODUCT_AMOUNT);
        HttpSession session = request.getSession();
        String page = String.valueOf(session.getAttribute(SessionAttribute.PAGE));
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        try {
            if(NumberValidator.isNumberValid(amount)) {
                Optional<Product> optionalProduct = productService.findProductById(idStr);
                if (optionalProduct.isPresent()) {
                    if (optionalProduct.get().getStatus().equals(TypeStatus.ACTIVE)) {
                        cart.addProduct(optionalProduct.get(), Integer.parseInt(amount));
                        session.setAttribute(SessionAttribute.CART, cart);
                        request.setAttribute(RequestParameter.MESSAGE, MessageKey.SUCCESSFUL_ADD_TO_CART);
                    } else {
                        cart.deleteProduct(optionalProduct.get());
                        request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_PRODUCT_NOT_ACTIVE);
                    }
                } else {
                    request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_UNKNOWN_PRODUCT);
                }
            } else {
                request.setAttribute(RequestParameter.MESSAGE,MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
        } catch (ServiceException e) {
            log.error(e);
            page = PagePath.PAGE_500;
        }
        return Router.redirectTo(page);
    }
}
