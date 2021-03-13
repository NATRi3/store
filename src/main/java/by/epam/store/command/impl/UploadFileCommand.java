package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ImageService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.service.UserService;
import by.epam.store.util.*;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

public class UploadFileCommand implements Command {
    private final static Logger log = LogManager.getLogger(UploadFileCommand.class);
    private static final UserService BASE_USER_SERVICE = ServiceCreator.getInstance().getUserService();
    private static final String PREFIX_SUCCESS = "successful";

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        String page = (String) session.getAttribute(SessionAttribute.PAGE);
        Router router = Router.forwardTo(page);
        try {
            Optional<String> fileName = changeImageVar(request, BASE_USER_SERVICE, String.valueOf(user.getId()));
            fileName.ifPresent(user::setImageName);
        } catch (ServiceException | IOException | FileUploadException e) {
            log.error(e);
            router = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return router;
    }

    static Optional<String> changeImageVar(HttpServletRequest request,
                                           ImageService imageService,
                                           String id) throws ServiceException, FileUploadException, IOException {
        ServletFileUpload upload = FileUtil.createUpload();
        Optional<String> optionalFileName = FileUtil.saveFile(upload.parseRequest(request));
        if (optionalFileName.isPresent()) {
            String message = imageService.changeImage(id, optionalFileName.get());
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, message);
        } else {
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_MESSAGE_WRONG_FILE_TYPE);
        }
        return optionalFileName;
    }
}
