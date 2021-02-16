package by.epam.store.util;

import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.util.Optional;
import java.util.Set;

public class FileUtil {
    private static final String SAVE_DIR = "C:/projectImg/";
    private static final Set<String> FILE_TYPE = Set.of("image/jpg", "image/png", "image/gif", "image/jpeg");
    public static Optional<String> saveFile(FileItem fileItem) throws Exception {
        Optional<String> resultFileName = Optional.empty();
        if (!fileItem.isFormField()) {
            if (FILE_TYPE.contains(fileItem.getContentType())) {
                String nameNewFile = FileNameGenerator.generate(fileItem.getName());
                File file = new File(SAVE_DIR +
                        nameNewFile);
                fileItem.write(file);
                resultFileName = Optional.of(nameNewFile);
            }
        }
        return resultFileName;
    }
}
