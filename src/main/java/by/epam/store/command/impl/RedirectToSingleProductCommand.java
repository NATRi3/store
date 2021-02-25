package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.entity.Product;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class RedirectToSingleProductCommand implements Command {
    private final static Logger log = LogManager.getLogger(RedirectToSingleProductCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public Router execute(HttpServletRequest request) {
        String id = request.getParameter(RequestParameter.ID_PRODUCT);
        try {
            Optional<Product> optionalProduct = productService.findProductById(id);
            if(!optionalProduct.isPresent()){
                return Router.redirectTo(PagePath.PAGE_404,request);
            }
            optionalProduct.ifPresent(product ->request.setAttribute(RequestParameter.PRODUCT,product));
            return Router.forwardTo(PagePath.SINGLE_PRODUCT,request);
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
            return Router.redirectTo(PagePath.PAGE_500,request);
        }
    }
}
