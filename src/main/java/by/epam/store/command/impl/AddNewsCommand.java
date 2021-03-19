package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.NewsService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The Add news command.
 */
public class AddNewsCommand implements Command {
    private final static Logger log = LogManager.getLogger(AddNewsCommand.class);
    private static final NewsService BASE_NEWS_SERVICE = ServiceCreator.getInstance().getNewsService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String> parameters = RequestUtil.getAllParametersFrom(request);
        try {
            String message = BASE_NEWS_SERVICE.createNews(parameters);
            page = RouterResponseHelper.router(request, message, parameters, PagePath.ADMIN_PANEL_NEWS);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
