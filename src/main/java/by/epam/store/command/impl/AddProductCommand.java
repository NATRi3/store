package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
@Log4j2
public class AddProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String name = request.getParameter(RequestParameter.NAME_PRODUCT);
        String info = request.getParameter(RequestParameter.INFO_PRODUCT);
        String price = request.getParameter(RequestParameter.PRICE_PRODUCT);
        String idCollection = request.getParameter(RequestParameter.ID_COLLECTION);
        try{
            productService.saveProduct(name,info,price,idCollection);
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
        }
        return PagePath.ADMIN_PANEL;
    }
}
