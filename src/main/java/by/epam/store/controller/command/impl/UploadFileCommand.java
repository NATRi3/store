package by.epam.store.controller.command.impl;

import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import by.epam.store.controller.Router;
import by.epam.store.model.entity.User;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.ImageService;
import by.epam.store.model.service.UserService;
import by.epam.store.util.*;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * The Upload file command.
 */
public class UploadFileCommand implements Command {
    private final static Logger log = LogManager.getLogger(UploadFileCommand.class);
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        String page = (String) session.getAttribute(SessionAttribute.PAGE);
        Router router = Router.forwardTo(page);
        try {
            Optional<String> fileName = changeImageVar(request, userService, String.valueOf(user.getId()));
            fileName.ifPresent(user::setImageName);
        } catch (CommandException e) {
            log.error(e);
            router = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return router;
    }

    /**
     * Change image var optional.
     *
     * @param request      the request
     * @param imageService the image service
     * @param id           the id
     * @return the optional
     * @throws ServiceException    the service exception
     * @throws FileUploadException the file upload exception
     * @throws IOException         the io exception
     */
    static Optional<String> changeImageVar(HttpServletRequest request,
                                           ImageService imageService,
                                           String id) throws CommandException {
        try {
            ServletFileUpload upload = FileUtil.createUpload();
            Optional<String> optionalFileName = FileUtil.saveFile(upload.parseRequest(request));
            if (optionalFileName.isPresent()) {
                String message = imageService.changeImage(id, optionalFileName.get());
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, message);
            } else {
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, MessageKey.ERROR_MESSAGE_WRONG_FILE_TYPE);
            }
            return optionalFileName;
        } catch (FileUploadException | ServiceException | IOException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
