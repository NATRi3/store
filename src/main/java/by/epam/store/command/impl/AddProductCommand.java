package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddProductCommand implements Command {
    private final static Logger log = LogManager.getLogger(AddProductCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public Router execute(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameter.NAME_PRODUCT,request.getParameter(RequestParameter.NAME_PRODUCT));
        parameters.put(RequestParameter.INFO_PRODUCT,request.getParameter(RequestParameter.INFO_PRODUCT));
        parameters.put(RequestParameter.PRICE_PRODUCT,request.getParameter(RequestParameter.PRICE_PRODUCT));
        parameters.put(RequestParameter.ID_COLLECTION,request.getParameter(RequestParameter.ID_COLLECTION));
        try{
            String message = productService.saveProduct(parameters);
            request.setAttribute(RequestParameter.MESSAGE,message);
            for(Map.Entry<String,String> entry: parameters.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
            return Router.forwardTo(PagePath.ADMIN_PANEL);
        } catch (ServiceException e) {
            log.error(e);
            return Router.redirectTo(PagePath.PAGE_500);
        }
    }
}
