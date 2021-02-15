package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
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
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;
@Log4j2
public class AddAmountProductToCartCommand implements Command {
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public String execute(HttpServletRequest request) {
        String page;
        String idStr = request.getParameter(RequestParameter.ID_PRODUCT);
        String amount = request.getParameter(RequestParameter.PRODUCT_AMOUNT);
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
        try {
            if(NumberValidator.isNumberValid(amount)) {
                Optional<Product> optionalProduct = productService.findProductById(idStr);
                if (optionalProduct.isPresent()) {
                    if (optionalProduct.get().getStatus().equals(TypeStatus.ACTIVE)) {
                        cart.addProduct(optionalProduct.get(), Integer.parseInt(amount));
                        session.setAttribute(SessionAttribute.CART, cart);
                        page = String.valueOf(session.getAttribute(SessionAttribute.PAGE));
                        request.setAttribute(RequestParameter.MESSAGE, MessageKey.SUCCESSFUL_ADD_TO_CART);
                    } else {
                        cart.deleteProduct(optionalProduct.get());
                        page = String.valueOf(session.getAttribute(SessionAttribute.PAGE));
                        request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_PRODUCT_NOT_ACTIVE);
                    }
                } else {
                    page = String.valueOf(session.getAttribute(SessionAttribute.PAGE));
                    request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_UNKNOWN_PRODUCT);
                }
            } else {
                page = String.valueOf(session.getAttribute(SessionAttribute.PAGE));
                request.setAttribute(RequestParameter.MESSAGE,MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
        } catch (ServiceException e) {
            log.error(e);
            page = PagePath.PAGE_500;
        }
        return page;
    }
}
