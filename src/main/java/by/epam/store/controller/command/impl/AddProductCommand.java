package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.ProductService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The Add product command.
 */
public class AddProductCommand implements Command {
    private final static Logger log = LogManager.getLogger(AddProductCommand.class);
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String[]> parameters = RequestUtil.getAllParametersFrom(request);
        try {
            String message = productService.saveProduct(parameters);
            page = RouterResponseHelper.router(request, message, parameters, PagePath.ADMIN_PANEL);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
