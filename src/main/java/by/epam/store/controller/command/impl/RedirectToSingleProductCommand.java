package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.model.entity.Product;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.ProductService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * The Redirect to single product command.
 */
public class RedirectToSingleProductCommand implements Command {
    private final static Logger log = LogManager.getLogger(RedirectToSingleProductCommand.class);
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router result;
        String id = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        try {
            Optional<Product> optionalProduct = productService.findProductById(id);
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
