package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
public class BlockProductCommand implements CommandAsync {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = request.getParameter(RequestParameter.ID_PRODUCT);
            productService.changeStatus(id, TypeStatus.BLOCKED);
            response.setContentType("application/text");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("success");
        } catch (ServiceException e) {
            log.error(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
