package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.RouterResponseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ChangeProductCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeProductCommand.class);
    private static final ProductService BASE_PRODUCT_SERVICE = ServiceCreator.getInstance().getProductService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String> parameters = RequestUtil.getAllParametersFrom(request);
        try {
            String message = BASE_PRODUCT_SERVICE.changeProduct(parameters);
            page = RouterResponseHelper.router(request, message, PagePath.ADMIN_PANEL);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
