package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.NewsService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddNewsCommand implements Command {
    private final static Logger log = LogManager.getLogger(AddNewsCommand.class);
    private static final NewsService newsService = ServiceCreator.getInstance().getNewsService();
    @Override
    public Router execute(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(RequestParameter.NEWS_INFO,request.getParameter(RequestParameter.NEWS_INFO));
        parameters.put(RequestParameter.NEWS_TITLE,request.getParameter(RequestParameter.NEWS_TITLE));
        try{
            String message = newsService.createNews(parameters);
            request.setAttribute(RequestParameter.MESSAGE,message);
            for(Map.Entry<String,String> entry: parameters.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
            return Router.forwardTo(PagePath.ADMIN_PANEL_NEWS);
        } catch (ServiceException e) {
            log.error(e);
            return Router.redirectTo(PagePath.PAGE_500);
        }
    }
}
