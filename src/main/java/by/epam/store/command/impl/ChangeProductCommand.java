package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ChangeProductCommand implements Command {
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public String execute(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameter.INFO_PRODUCT, request.getParameter(RequestParameter.INFO_PRODUCT));
        parameters.put(RequestParameter.PRICE_PRODUCT, request.getParameter(RequestParameter.PRICE_PRODUCT));
        parameters.put(RequestParameter.ID_PRODUCT, request.getParameter(RequestParameter.ID_PRODUCT));
        try {
            String message = productService.changeProduct(parameters);
            request.setAttribute(RequestParameter.MESSAGE, message);
            for(Map.Entry<String,String> entry: parameters.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
        }
        return PagePath.ADMIN_PANEL;
    }
}
