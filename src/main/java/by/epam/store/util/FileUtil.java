package by.epam.store.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FileUtil {
    private static final Set<String> FILE_TYPE = Set.of("image/jpg", "image/png", "image/gif", "image/jpeg");
    public static final String CLOUD_NAME = "cloud_name";
    public static final String NAME = "defsuomst";
    public static final String API_KEY = "api_key";
    public static final String KEY = "233366538399385";
    public static final String API_SECRET = "api_secret";
    public static final String SECRET = "gThYjOqO9lfS6A3wPOixNMlbSv4";
    public static final String URL = "url";
    private static final int FILE_MAX_SIZE = 1024 * 1024;
    private static final int MEM_MAX_SIZE = 1024 * 1024 * 5;

    private FileUtil() {
    }

    public static ServletFileUpload createUpload() {
        DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
        diskFileItemFactory.setSizeThreshold(MEM_MAX_SIZE);
        ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
        upload.setSizeMax(FILE_MAX_SIZE);
        return upload;
    }

    public static Optional<String> saveFile(List<FileItem> fileItems) throws IOException {
        for (FileItem fileItem : fileItems) {
            if (!fileItem.isFormField()) {
                if (FILE_TYPE.contains(fileItem.getContentType())) {
                    byte[] file = fileItem.get();
                    Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                            CLOUD_NAME, NAME,
                            API_KEY, KEY,
                            API_SECRET, SECRET
                    ));
                    Map<String, Object> result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                    return Optional.of((String) result.get(URL));
                }
            }
        }
        return Optional.empty();
    }
}
