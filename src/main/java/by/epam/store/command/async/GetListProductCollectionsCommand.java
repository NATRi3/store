package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.ProductCollection;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.CollectionService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.command.RequestParameterAndAttribute;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The Get list product collections command.
 */
public class GetListProductCollectionsCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListProductCollectionsCommand.class);
    private static final CollectionService BASE_PRODUCT_COLLECTION_SERVICE = ServiceCreator.getInstance().getCollectionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String status = request.getParameter(RequestParameterAndAttribute.TYPE_STATUS);
        try {
            List<ProductCollection> collectionList = BASE_PRODUCT_COLLECTION_SERVICE.findAllProductCollections();
            String json = new Gson().toJson(collectionList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
