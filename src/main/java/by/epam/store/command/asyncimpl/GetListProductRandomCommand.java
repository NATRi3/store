package by.epam.store.command.asyncimpl;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Product;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.RequestParameter;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class GetListProductRandomCommand implements CommandAsync {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String amount = request.getParameter(RequestParameter.PRODUCT_AMOUNT);
            List<Product> randomProduct = productService.findRandomProduct(amount);
            String json = new Gson().toJson(randomProduct);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (ServiceException | IOException e) {
            log.error(e);
        }

    }
}
