package by.epam.store.util;

import by.epam.store.exception.ServiceException;
import by.epam.store.exception.UtilException;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.List;
import java.util.Set;

public class FileUtil {
    private static final String SAVE_DIR = "C:/projectImg/";
    private static final Set<String> FILE_TYPE = Set.of("image/jpg", "image/png", "image/gif", "image/jpeg");
    public static String saveFile(List<FileItem> fileItems) throws UtilException {
        for (FileItem fileItem : fileItems) {
            if (!fileItem.isFormField()) {
                if (FILE_TYPE.contains(fileItem.getContentType())) {
                    String nameNewFile = FileNameGenerator.generate(fileItem.getName());
                    File file = new File(SAVE_DIR +
                            nameNewFile);
                    try {
                        fileItem.write(file);
                        return nameNewFile;
                    } catch (Exception e) {
                        throw new UtilException(MessageErrorKey.ERROR_UPLOAD_FILE);
                    }
                } else {
                    throw new UtilException(MessageErrorKey.ERROR_MESSAGE_WRONG_FILE_TYPE);
                }
            }
        }
        throw new UtilException(MessageErrorKey.ERROR_UPLOAD_FILE);
    }
}
