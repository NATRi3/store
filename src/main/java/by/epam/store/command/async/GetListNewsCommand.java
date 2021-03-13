package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.News;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.NewsService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.ResponseWriterUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class GetListNewsCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListNewsCommand.class);
    private static final NewsService BASE_NEWS_SERVICE = ServiceCreator.getInstance().getNewsService();

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
