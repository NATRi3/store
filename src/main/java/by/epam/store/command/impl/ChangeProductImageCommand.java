package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.*;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;

public class ChangeProductImageCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeProductImageCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        String id = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        ServletFileUpload upload = FileUtil.createUpload();
        try {
            Optional<String> optionalFileName = FileUtil.saveFile(upload.parseRequest(request));
            page = ImageUtil
                    .changeImage(request, productService,
                            id, optionalFileName,
                            PagePath.ADMIN_PANEL, MessageKey.ERROR_MESSAGE_WRONG_FILE_TYPE);
        } catch (FileUploadException | IOException e) {
            log.error(e);
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_UPLOAD_FILE);
            page = Router.forwardTo(PagePath.ADMIN_PANEL, request);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
