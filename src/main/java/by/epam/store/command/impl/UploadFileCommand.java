package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Log4j2
public class UploadFileCommand implements Command {
    private static final String SAVE_DIR = "D:/projectImg/";
    static final int FILE_MAX_SIZE = 1024 * 1024;
    static final int MEM_MAX_SIZE = 1024 * 1024 * 5;
    private static final String FILE_TYPE = "image/jpg, image/png, image/gif, image/jpeg";
    @Override
    public String execute(HttpServletRequest request) {
        String page = PagePath.ACCOUNT_PAGE;
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setRepository(new File(SAVE_DIR));
        diskFileItemFactory.setSizeThreshold(MEM_MAX_SIZE);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(FILE_MAX_SIZE);
        try {
            List<FileItem> fileItems = upload.parseRequest(request);
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {
                    if(FILE_TYPE.contains(fileItem.getContentType())){
                        String fileName = fileNameGenerator(fileItem.getName());
                        File file = new File(SAVE_DIR +
                                fileName);
                        fileItem.write(file);
                    } else {
                        request.setAttribute(RequestParameter.MESSAGE, MessageErrorKey.ERROR_MESSAGE_WRONG_FILE_TYPE);
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
            request.setAttribute(RequestParameter.MESSAGE,MessageErrorKey.ERROR_UPLOAD_FILE);
        }
        return page;
    }
    private String fileNameGenerator(String fileName){
        StringBuilder result = new StringBuilder();
        result.append(UUID.randomUUID());
        String typeFile = fileName.substring(fileName.indexOf(".")+1);
        result.append(typeFile);
        return result.toString();
    }
}
