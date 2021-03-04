package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Product;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.ResponseWriterUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetListProductByCollectionCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListProductByCollectionCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idCollection = request.getParameter(RequestParameter.ID_COLLECTION);
        String typeSort = request.getParameter(RequestParameter.TYPE_SORT);
        String begin = request.getParameter(RequestParameter.BEGIN_PAGINATION);
        String status = request.getParameter(RequestParameter.TYPE_STATUS);
        try {
            List<Product> productList =
                    productService.findProductByCollectionAndSort(idCollection, typeSort, status, begin);
            String json = new Gson().toJson(productList);
            ResponseWriterUtil.writeJsonToResponse(response,json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
