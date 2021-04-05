package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.model.entity.Cart;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.ProductService;
import by.epam.store.annotation.Autowired;
import by.epam.store.util.MessageKey;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import by.epam.store.model.validator.NumberValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * The Add amount product to cart command.
 */
public class AddAmountProductToCartCommand implements Command {
    private final static Logger log = LogManager.getLogger(AddAmountProductToCartCommand.class);
    private ProductService productService;
    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        String idStr = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        String amount = request.getParameter(RequestParameterAndAttribute.PRODUCT_AMOUNT);
        HttpSession session = request.getSession();
        String page = String.valueOf(session.getAttribute(SessionAttribute.PAGE));
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        try {
            if (NumberValidator.isLongValid(amount)) {
                Optional<Product> optionalProduct = productService.findProductById(idStr);
                if (optionalProduct.isPresent()) {
                    if (optionalProduct.get().getStatus().equals(TypeStatus.ACTIVE)) {
                        cart.replaceProduct(optionalProduct.get(), Integer.parseInt(amount));
                        session.setAttribute(SessionAttribute.CART, cart);
                        request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.SUCCESSFUL_ADD_TO_CART);
                    } else {
                        cart.deleteProduct(optionalProduct.get());
                        request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_PRODUCT_NOT_ACTIVE);
                    }
                } else {
                    request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_UNKNOWN_PRODUCT);
                }
            } else {
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
        } catch (ServiceException e) {
            log.error(e);
            page = PagePath.PAGE_500;
        }
        return Router.redirectTo(page, request);
    }
}
