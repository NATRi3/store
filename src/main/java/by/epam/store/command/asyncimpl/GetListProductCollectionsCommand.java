package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.ProductCollection;
import by.epam.store.exception.ServiceException;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Log4j2
public class GetListProductCollectionsCommand implements CommandAsync {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<ProductCollection> collectionList = productCollectionService.getAllProductCollections();
            String json = new Gson().toJson(collectionList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (ServiceException | IOException e) {
            log.error(e);
        }
    }
}