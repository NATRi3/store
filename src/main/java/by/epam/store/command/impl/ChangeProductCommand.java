package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
@Log4j2
public class ChangeProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String info = request.getParameter(RequestParameter.INFO_PRODUCT);
        String price = request.getParameter(RequestParameter.PRICE_PRODUCT);
        String id = request.getParameter(RequestParameter.ID_PRODUCT);
        try {
            productService.changeProduct(id,info,price);
            request.setAttribute(RequestParameter.MESSAGE, MessageErrorKey.SUCCESSFUL_CHANGE_PRODUCT);
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
        }
        return PagePath.ADMIN_PANEL;
    }
}
