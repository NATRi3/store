package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
@Log4j2
public class ChangeProductImageCommand implements Command {
    private static final String SAVE_DIR = "C:/projectImg/";
    static final int FILE_MAX_SIZE = 1024 * 1024;
    static final int MEM_MAX_SIZE = 1024 * 1024 * 5;

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String id = request.getParameter(RequestParameter.ID_PRODUCT);
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(new File(SAVE_DIR));
        diskFileItemFactory.setSizeThreshold(MEM_MAX_SIZE);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(FILE_MAX_SIZE);
        try {
            String s = productService.changeProductImage(id,upload.parseRequest(request));
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE, e.getMessage());
        } catch (FileUploadException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE, MessageErrorKey.ERROR_UPLOAD_FILE);
        }
        return PagePath.ADMIN_PANEL;
    }
}
