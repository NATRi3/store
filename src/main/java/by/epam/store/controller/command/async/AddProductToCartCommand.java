package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.model.entity.Cart;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.ProductService;
import by.epam.store.annotation.Autowired;
import by.epam.store.util.MessageKey;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * The Add product to cart command.
 */
public class AddProductToCartCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(AddProductToCartCommand.class);
    private ProductService BASE_PRODUCT_SERVICE;

    @Autowired
    public void setBASE_PRODUCT_SERVICE(ProductService BASE_PRODUCT_SERVICE) {
        this.BASE_PRODUCT_SERVICE = BASE_PRODUCT_SERVICE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        String messageKey;
        try {
            Optional<Product> optionalProduct = BASE_PRODUCT_SERVICE.findProductById(idStr);
            if (optionalProduct.isPresent()) {
                if (optionalProduct.get().getStatus().equals(TypeStatus.ACTIVE)) {
                    HttpSession session = request.getSession();
                    Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
                    cart.addProduct(optionalProduct.get());
                    session.setAttribute(SessionAttribute.CART, cart);
                    messageKey = MessageKey.SUCCESSFUL_ADD_TO_CART;
                } else {
                    messageKey = MessageKey.ERROR_PRODUCT_NOT_ACTIVE;
                }
            } else {
                messageKey = MessageKey.ERROR_UNKNOWN_PRODUCT;
            }
            ResponseWriterUtil.writeTextToResponse(request, response, messageKey);
        } catch (ServiceException e) {
            log.warn(e);
            response.setStatus(500);
        }
    }
}
