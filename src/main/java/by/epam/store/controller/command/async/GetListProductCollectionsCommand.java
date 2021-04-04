package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.model.entity.ProductCollection;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.CollectionService;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.annotation.Autowired;
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
    private CollectionService collectionService;

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String status = request.getParameter(RequestParameterAndAttribute.TYPE_STATUS);
        try {
            List<ProductCollection> collectionList = collectionService.findAllProductCollections();
            String json = new Gson().toJson(collectionList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
