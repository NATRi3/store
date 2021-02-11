package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
@Log4j2
public class AddNewsCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String title = request.getParameter(RequestParameter.NEWS_TITLE);
        String info = request.getParameter(RequestParameter.NEWS_INFO);
        try{
            newsService.createNews(title,info);
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE,e.getMessage());
        }
        return PagePath.ADMIN_PANEL_NEWS;
    }
}
