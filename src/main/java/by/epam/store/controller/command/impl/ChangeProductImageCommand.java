package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.CommandException;
import by.epam.store.model.service.ProductService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import static by.epam.store.controller.command.impl.UploadFileCommand.changeImageVar;

/**
 * The Change product image command.
 */
public class ChangeProductImageCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeProductImageCommand.class);
    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router page = Router.forwardTo(PagePath.ADMIN_PANEL);
        String id = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        try {
            changeImageVar(request, productService, id);
        } catch (CommandException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
