package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
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
    private static final UserService userService = ServiceCreator.getInstance().getUserService();
    private static final String PREFIX_SUCCESS = "successful";
    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        ServletFileUpload upload = FileUtil.createUpload();
        String currentImage = user.getImageName();
        try {
            Optional<String> optionalFileName = FileUtil.saveFile(upload.parseRequest(request));
            page = ImageUtil
                    .changeImage(request, userService,
                            String.valueOf(user.getId()), optionalFileName,
                            PagePath.ACCOUNT, MessageKey.ERROR_MESSAGE_WRONG_FILE_TYPE);
            if(!request.getAttribute(RequestParameterAndAttribute.MESSAGE).toString().contains(PREFIX_SUCCESS)){
                user.setImageName(currentImage);
            }
        } catch (IOException | FileUploadException e){
            user.setImageName(currentImage);
            log.error(e);
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_UPLOAD_FILE);
            page = Router.forwardTo(PagePath.ADMIN_PANEL, request);
        } catch(ServiceException e) {
            user.setImageName(currentImage);
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
