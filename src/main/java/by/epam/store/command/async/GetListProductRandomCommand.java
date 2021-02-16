package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Product;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.RequestParameter;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetListProductRandomCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListProductRandomCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            try {
                String amount = request.getParameter(RequestParameter.PRODUCT_AMOUNT);
                List<Product> randomProduct = productService.findRandomProduct(amount);
                String json = new Gson().toJson(randomProduct);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (ServiceException e) {
                log.error(e);
                response.sendError(500);
            }
        }catch (IOException e){
            log.error(e);
        }
    }
}
