package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Cart;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.MessageCreator;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class AddProductToCartCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(AddProductToCartCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String idStr = request.getParameter(RequestParameter.ID_PRODUCT);
            String messageKey;
            try {
                Optional<Product> optionalProduct = productService.findProductById(idStr);
                if (optionalProduct.isPresent()){
                    if(optionalProduct.get().getStatus().equals(TypeStatus.ACTIVE)) {
                    HttpSession session = request.getSession();
                    Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
                    cart.addProduct(optionalProduct.get());
                    session.setAttribute(SessionAttribute.CART, cart);
                    messageKey = MessageKey.SUCCESSFUL_ADD_TO_CART;
                    } else {
                        messageKey = MessageKey.ERROR_PRODUCT_NOT_ACTIVE;
                    }
                }else {
                    messageKey = MessageKey.ERROR_UNKNOWN_PRODUCT;
                }
                String message = MessageCreator.getMessageFromBundleByLocale(messageKey,request);
                response.setContentType("application/text");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(message);
            } catch (ServiceException e) {
                log.warn(e);
                response.setStatus(500);
            }
        } catch (IOException e) {
            log.error(e);
        }
    }
}
