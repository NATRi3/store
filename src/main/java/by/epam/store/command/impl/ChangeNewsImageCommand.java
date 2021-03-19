package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.NewsService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.PagePath;
import by.epam.store.command.RequestParameterAndAttribute;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static by.epam.store.command.impl.UploadFileCommand.changeImageVar;

/**
 * The Change news image command.
 */
public class ChangeNewsImageCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeNewsImageCommand.class);
    private static final NewsService BASE_NEWS_SERVICE = ServiceCreator.getInstance().getNewsService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page = Router.forwardTo(PagePath.ADMIN_PANEL_NEWS);
        String id = request.getParameter(RequestParameterAndAttribute.ID_NEWS);
        try {
            changeImageVar(request, BASE_NEWS_SERVICE, id);
        } catch (ServiceException | FileUploadException | IOException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
