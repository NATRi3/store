package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;

public class ChangeProductImageCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeProductImageCommand.class);
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();
    private static final String SAVE_DIR = "C:/projectImg/";
    private static final int FILE_MAX_SIZE = 1024 * 1024;
    private static final int MEM_MAX_SIZE = 1024 * 1024 * 5;

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
            String realPath = request.getServletContext().getRealPath("");
            String message = productService.changeProductImage(id,upload.parseRequest(request),realPath);
            request.setAttribute(RequestParameter.MESSAGE,message);
        } catch (ServiceException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE, e.getMessage());
        } catch (FileUploadException e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE, MessageKey.ERROR_UPLOAD_FILE);
        }
        return PagePath.ADMIN_PANEL;
    }
}
