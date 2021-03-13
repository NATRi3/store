package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.ResponseWriterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeProductStatusCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(ChangeProductStatusCommand.class);
    private static final ProductService BASE_PRODUCT_SERVICE = ServiceCreator.getInstance().getProductService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String id = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
            String status = request.getParameter(RequestParameterAndAttribute.TYPE_STATUS);
            String messageKey = BASE_PRODUCT_SERVICE.changeStatus(id, TypeStatus.NONACTIVE);
            ResponseWriterUtil.writeTextToResponse(request, response, messageKey);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
