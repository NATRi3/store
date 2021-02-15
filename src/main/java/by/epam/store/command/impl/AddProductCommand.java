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
import java.util.Optional;

@Log4j2
public class AddProductCommand implements Command {
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public String execute(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameter.NAME_PRODUCT,request.getParameter(RequestParameter.NAME_PRODUCT));
        parameters.put(RequestParameter.INFO_PRODUCT,request.getParameter(RequestParameter.INFO_PRODUCT));
        parameters.put(RequestParameter.PRICE_PRODUCT,request.getParameter(RequestParameter.PRICE_PRODUCT));
        parameters.put(RequestParameter.ID_COLLECTION,request.getParameter(RequestParameter.ID_COLLECTION));
        try{
            Optional<String> optionalMessage = productService.saveProduct(parameters);
            optionalMessage.ifPresent(s -> request.setAttribute(RequestParameter.MESSAGE,s));
            for(Map.Entry<String,String> entry: parameters.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
            return PagePath.ADMIN_PANEL;
        } catch (ServiceException e) {
            log.error(e);
            return PagePath.PAGE_500;
        }
    }
}
