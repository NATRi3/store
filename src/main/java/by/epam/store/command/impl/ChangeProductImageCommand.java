package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.PagePath;
import by.epam.store.command.RequestParameterAndAttribute;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static by.epam.store.command.impl.UploadFileCommand.changeImageVar;

/**
 * The Change product image command.
 */
public class ChangeProductImageCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeProductImageCommand.class);
    private static final ProductService BASE_PRODUCT_SERVICE = ServiceCreator.getInstance().getProductService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page = Router.forwardTo(PagePath.ADMIN_PANEL);
        String id = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        try {
            changeImageVar(request, BASE_PRODUCT_SERVICE, id);
        } catch (CommandException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
