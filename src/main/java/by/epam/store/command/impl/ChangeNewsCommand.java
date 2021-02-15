package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.NewsService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class ChangeNewsCommand implements Command {
    private static final NewsService newsService = ServiceCreator.getInstance().getNewsService();
    @Override
    public String execute(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameter.NEWS_TITLE,request.getParameter(RequestParameter.NEWS_TITLE));
        parameters.put(RequestParameter.NEWS_INFO,request.getParameter(RequestParameter.NEWS_INFO));
        parameters.put(RequestParameter.ID_NEWS,request.getParameter(RequestParameter.ID_NEWS));
        try{
            String message = newsService.changeNews(parameters);
            request.setAttribute(RequestParameter.MESSAGE,message);
            for(Map.Entry<String,String> entry: parameters.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
            return PagePath.ADMIN_PANEL_NEWS;
        } catch (ServiceException e) {
            log.error(e);
            return PagePath.PAGE_500;
        }
    }
}
