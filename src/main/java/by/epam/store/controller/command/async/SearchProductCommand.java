package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.model.entity.Product;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.ProductService;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.annotation.Autowired;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The Search product command.
 */
public class SearchProductCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(SearchProductCommand.class);
    private ProductService BASE_PRODUCT_SERVICE;

    @Autowired
    public void setBASE_PRODUCT_SERVICE(ProductService BASE_PRODUCT_SERVICE) {
        this.BASE_PRODUCT_SERVICE = BASE_PRODUCT_SERVICE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String search = request.getParameter(RequestParameterAndAttribute.SEARCH);
            List<Product> productList = BASE_PRODUCT_SERVICE.searchProduct(search);
            String json = new Gson().toJson(productList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
