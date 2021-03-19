package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.News;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.NewsService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.command.RequestParameterAndAttribute;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The Get fresh news command.
 */
public class GetFreshNewsCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetFreshNewsCommand.class);
    private static final NewsService BASE_NEWS_SERVICE = ServiceCreator.getInstance().getNewsService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        List<News> productList;
        String count = request.getParameter(RequestParameterAndAttribute.NEWS_AMOUNT);
        try {
            productList = BASE_NEWS_SERVICE.findFreshNews(count);
            String json = new Gson().toJson(productList);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
