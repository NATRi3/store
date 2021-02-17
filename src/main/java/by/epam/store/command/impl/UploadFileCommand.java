package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.FileUtil;
import by.epam.store.util.PagePath;
import by.epam.store.util.SessionAttribute;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UploadFileCommand implements Command {
    private final static Logger log = LogManager.getLogger(UploadFileCommand.class);
    private static final UserService userService = ServiceCreator.getInstance().getUserService();
    private static final int FILE_MAX_SIZE = 1024 * 1024;
    private static final int MEM_MAX_SIZE = 1024 * 1024 * 5;

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(MEM_MAX_SIZE);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(FILE_MAX_SIZE);
        try {
            Optional<String> optionalFileName = FileUtil.saveFile(upload.parseRequest(request));
            if(optionalFileName.isPresent()) {
                user.setImageName(optionalFileName.get());
                userService.updateById(user);
            }
        } catch (IOException | FileUploadException | ServiceException e) {
            log.error(e);
            return PagePath.PAGE_500;
        }
        return PagePath.ACCOUNT;
    }


    public static void main(String[] args) throws GeneralSecurityException, IOException {
        byte[] file = Files.readAllBytes(Paths.get("C:\\Users\\ssykh\\IdeaProjects\\store\\src\\main\\webapp\\images\\page\\logo.png"));
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "defsuomst",
                "api_key", "233366538399385",
                "api_secret", "gThYjOqO9lfS6A3wPOixNMlbSv4"
        ));
        cloudinary.uploader().upload(file,ObjectUtils.emptyMap());
    }
}
