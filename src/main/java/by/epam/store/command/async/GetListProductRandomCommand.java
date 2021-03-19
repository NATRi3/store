package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Product;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.command.RequestParameterAndAttribute;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The Get list product random command.
 */
public class GetListProductRandomCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListProductRandomCommand.class);
    private static final ProductService BASE_PRODUCT_SERVICE = ServiceCreator.getInstance().getProductService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String amount = request.getParameter(RequestParameterAndAttribute.PRODUCT_AMOUNT);
            List<Product> randomProduct = BASE_PRODUCT_SERVICE.findRandomProduct(amount);
            String json = new Gson().toJson(randomProduct);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
