package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.ProductService;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Activate product command.
 */
public class ActivateProductCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(ActivateProductCommand.class);

    private ProductService productService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String id = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
            String messageKey = productService.changeStatus(id, TypeStatus.ACTIVE);
            ResponseWriterUtil.writeTextToResponse(request, response, messageKey);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
