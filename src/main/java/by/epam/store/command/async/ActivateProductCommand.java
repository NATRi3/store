package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.MessageCreator;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ActivateProductCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(ActivateProductCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            try {
                String id = request.getParameter(RequestParameter.ID_PRODUCT);
                String messageKey = productService.changeStatus(id, TypeStatus.ACTIVE);
                String message = MessageCreator.getMessageFromBundleByLocale(messageKey,request);
                response.setContentType("application/text");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(message);
            } catch (ServiceException e) {
                log.error(e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (IOException ioException) {
            log.error(ioException);
        }

    }
}
