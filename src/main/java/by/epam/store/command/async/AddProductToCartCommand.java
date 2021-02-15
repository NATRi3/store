package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Cart;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@Log4j2
public class AddProductToCartCommand implements CommandAsync {
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String idStr = request.getParameter(RequestParameter.ID_PRODUCT);
            String message;
            try {
                Optional<Product> optionalProduct = productService.findProductById(idStr);
                if (optionalProduct.isPresent()){
                    if(optionalProduct.get().getStatus().equals(TypeStatus.ACTIVE)) {
                    HttpSession session = request.getSession();
                    Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
                    cart.addProduct(optionalProduct.get());
                    session.setAttribute(SessionAttribute.CART, cart);
                    message = MessageKey.SUCCESSFUL_ADD_TO_CART;
                    } else {
                        message = MessageKey.ERROR_PRODUCT_NOT_ACTIVE;
                    }
                }else {
                    message = MessageKey.ERROR_UNKNOWN_PRODUCT;
                }
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
