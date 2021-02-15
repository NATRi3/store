package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Product;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Log4j2
public class RedirectToSingleProductCommand implements Command {
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public String execute(HttpServletRequest request) {
        String id = request.getParameter(RequestParameter.ID_PRODUCT);
        try {
            Optional<Product> optionalProduct = productService.findProductById(id);
            if(!optionalProduct.isPresent()){
                return PagePath.PAGE_404;
            }
            optionalProduct.ifPresent(product ->request.setAttribute(RequestParameter.PRODUCT,product));
            return PagePath.SINGLE_PRODUCT;
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
            return PagePath.PAGE_500;
        }
    }
}
