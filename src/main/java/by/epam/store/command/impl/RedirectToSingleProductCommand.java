package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.entity.Product;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RedirectToSingleProductCommand implements Command {
    private final static Logger log = LogManager.getLogger(RedirectToSingleProductCommand.class);
    private static final ProductService BASE_PRODUCT_SERVICE = ServiceCreator.getInstance().getProductService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router result;
        String id = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        try {
            Optional<Product> optionalProduct = BASE_PRODUCT_SERVICE.findProductById(id);
            if (!optionalProduct.isPresent()) {
                result = Router.redirectTo(PagePath.PAGE_404, request);
            } else {
                optionalProduct.ifPresent(product -> request.setAttribute(RequestParameterAndAttribute.PRODUCT, product));
                result = Router.forwardTo(PagePath.SINGLE_PRODUCT);
            }
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, e.getMessage());
            result = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return result;
    }
}
