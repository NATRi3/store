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
 * The Get list product by collection command.
 */
public class GetListProductByCollectionCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListProductByCollectionCommand.class);
    private static final ProductService BASE_PRODUCT_SERVICE = ServiceCreator.getInstance().getProductService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idCollection = request.getParameter(RequestParameterAndAttribute.ID_COLLECTION);
        String typeSort = request.getParameter(RequestParameterAndAttribute.TYPE_SORT);
        String begin = request.getParameter(RequestParameterAndAttribute.BEGIN_PAGINATION);
        String status = request.getParameter(RequestParameterAndAttribute.TYPE_STATUS);
        try {
            List<Product> productList =
                    BASE_PRODUCT_SERVICE.findProductByCollectionAndSort(idCollection, typeSort, status, begin);
            String json = new Gson().toJson(productList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
