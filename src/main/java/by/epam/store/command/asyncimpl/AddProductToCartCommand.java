package by.epam.store.command.asyncimpl;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Cart;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Log4j2
public class AddProductToCartCommand implements CommandAsync {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String idStr = request.getParameter(RequestParameter.ID_PRODUCT);
        RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.SHOP);
        try {
            Product product = productService.findProductById(idStr);
            if (product.getStatus().equals(TypeStatus.ACTIVE)) {
                HttpSession session = request.getSession();
                Cart cart = (Cart) session.getAttribute(SessionAttribute.CART);
                cart.addProduct(product, 1);
                session.setAttribute(SessionAttribute.CART, cart);

                response.setStatus(200);
                return;
            } else {
                request.setAttribute(RequestParameter.MESSAGE, MessageErrorKey.PRODUCT_NOT_ACTIVE);
            }
        } catch (ServiceException e) {
            log.warn(e);
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
        }
        try {
            dispatcher.forward(request,response);
        } catch (ServletException | IOException e) {
            log.error(e);
        }
    }
}
