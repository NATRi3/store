package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.NewsService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.RouterResponseHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class DeleteNewsCommand implements Command {
    private final static Logger log = LogManager.getLogger(DeleteNewsCommand.class);
    private static final NewsService newsService = ServiceCreator.getInstance().getNewsService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        String id = request.getParameter(RequestParameterAndAttribute.ID_NEWS);
        try {
            String message = newsService.deleteNews(id);
            page = RouterResponseHelper.router(request,message,PagePath.ADMIN_PANEL_NEWS);
        } catch (ServiceException e) {
            log.error(e);
            page =Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
