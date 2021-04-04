package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.model.entity.News;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.NewsService;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.annotation.Autowired;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The Get list news command.
 */
public class GetListNewsCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListNewsCommand.class);
    private NewsService BASE_NEWS_SERVICE;

    @Autowired
    public void setBASE_NEWS_SERVICE(NewsService BASE_NEWS_SERVICE) {
        this.BASE_NEWS_SERVICE = BASE_NEWS_SERVICE;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String typeSort = request.getParameter(RequestParameterAndAttribute.TYPE_SORT);
        String begin = request.getParameter(RequestParameterAndAttribute.BEGIN_PAGINATION);
        try {
            List<News> productList = BASE_NEWS_SERVICE.findSortNews(typeSort, begin);
            String json = new Gson().toJson(productList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
