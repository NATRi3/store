package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.NewsService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteNewsCommand implements Command {
    private final static Logger log = LogManager.getLogger(DeleteNewsCommand.class);
    private static final NewsService newsService = ServiceCreator.getInstance().getNewsService();
    @Override
    public String execute(HttpServletRequest request) {
        String id = request.getParameter(RequestParameter.ID_NEWS);
        try{
            String message = newsService.deleteNews(id);
            request.setAttribute(RequestParameter.MESSAGE,message);
            return PagePath.ADMIN_PANEL_NEWS;
        } catch (ServiceException e) {
            log.error(e);
            return PagePath.PAGE_500;
        }
    }
}
