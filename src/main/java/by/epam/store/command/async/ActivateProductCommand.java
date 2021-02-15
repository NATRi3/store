package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class ActivateProductCommand implements CommandAsync {
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            try {
            String id = request.getParameter(RequestParameter.ID_PRODUCT);
            String message = productService.changeStatus(id, TypeStatus.ACTIVE);
            response.setContentType("application/text");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(message);
            } catch (ServiceException | IOException e) {
                log.error(e);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (IOException ioException) {
            log.error(ioException);
        }

    }
}
