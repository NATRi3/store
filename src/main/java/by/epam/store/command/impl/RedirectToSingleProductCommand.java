package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.entity.Product;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
@Log4j2
public class RedirectToSingleProductCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String resultPage;
        String id = request.getParameter(RequestParameter.ID_PRODUCT);
        try {
            Product product = productService.findProductById(id);
            request.setAttribute(RequestParameter.PRODUCT,product);
            resultPage = PagePath.SINGLE_PRODUCT;
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
            resultPage = PagePath.SHOP;
        }
        return resultPage;
    }
}
