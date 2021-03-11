package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.ResponseWriterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivateProductCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(ActivateProductCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();

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
