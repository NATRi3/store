package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.ProductCollection;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductCollectionService;
import by.epam.store.util.RequestParameter;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetListProductCollectionsCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListProductCollectionsCommand.class);
    public static final ProductCollectionService productCollectionService = ServiceCreator.getInstance().getCollectionService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String status = request.getParameter(RequestParameter.TYPE_STATUS);
            try {
                List<ProductCollection> collectionList = productCollectionService.findAllProductCollections();
                String json = new Gson().toJson(collectionList);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(json);
            } catch (ServiceException e) {
                log.error(e);
                response.sendError(500);
            }
        } catch (IOException e){
            log.error(e);
        }
    }
}
