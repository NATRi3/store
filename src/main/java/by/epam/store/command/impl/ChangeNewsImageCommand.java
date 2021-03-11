package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.NewsService;
import by.epam.store.util.*;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class ChangeNewsImageCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeNewsImageCommand.class);
    private static final NewsService newsService = ServiceCreator.getInstance().getNewsService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        String id = request.getParameter(RequestParameterAndAttribute.ID_NEWS);
        ServletFileUpload upload = FileUtil.createUpload();
        try {
            Optional<String> optionalFileName = FileUtil.saveFile(upload.parseRequest(request));
            page = ImageUtil
                    .changeImage(request, newsService,
                            id, optionalFileName,
                            PagePath.ADMIN_PANEL_NEWS, MessageKey.ERROR_UPLOAD_FILE);
        } catch (FileUploadException | IOException e) {
            log.error(e);
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_UPLOAD_FILE);
            page = Router.forwardTo(PagePath.ADMIN_PANEL_NEWS, request);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
