package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.NewsService;
import by.epam.store.util.FileUtil;
import by.epam.store.util.MessageKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ChangeNewsImageCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeNewsImageCommand.class);
    private static final NewsService newsService = ServiceCreator.getInstance().getNewsService();
    private static final String SAVE_DIR = "C:/projectImg/";
    private static final int FILE_MAX_SIZE = 1024 * 1024;
    private static final int MEM_MAX_SIZE = 1024 * 1024 * 5;

    @Override
    public Router execute(HttpServletRequest request) {
        String id = request.getParameter(RequestParameter.ID_NEWS);
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(new File(SAVE_DIR));
        diskFileItemFactory.setSizeThreshold(MEM_MAX_SIZE);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(FILE_MAX_SIZE);
        try {
            Optional<String> optionalFileName = FileUtil.saveFile(upload.parseRequest(request));
            if(optionalFileName.isPresent()){
                newsService.changeImage(id,optionalFileName.get());
                return Router.redirectTo(PagePath.ADMIN_PANEL_NEWS,request);
            } else {
                request.setAttribute(RequestParameter.MESSAGE,MessageKey.ERROR_MESSAGE_WRONG_FILE_TYPE);
            }
        } catch (FileUploadException | IOException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_UPLOAD_FILE);
        } catch (ServiceException e) {
            log.error(e);
            return Router.redirectTo(PagePath.PAGE_500,request);
        }
        return Router.forwardTo(PagePath.ADMIN_PANEL_NEWS,request);
    }
}
