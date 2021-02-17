package by.epam.store.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FileUtil {
    private static final Set<String> FILE_TYPE = Set.of("image/jpg", "image/png", "image/gif", "image/jpeg");
    public static Optional<String> saveFile(List<FileItem> fileItems) throws IOException {
        for(FileItem fileItem: fileItems){
            if (!fileItem.isFormField()) {
                byte[] file = fileItem.get();
                Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                        "cloud_name", "defsuomst",
                        "api_key", "233366538399385",
                        "api_secret", "gThYjOqO9lfS6A3wPOixNMlbSv4"
                ));
                Map<String,Object> result = cloudinary.uploader().upload(file,ObjectUtils.emptyMap());
                return Optional.of((String) result.get("url"));
            }
        }
        return Optional.empty();
    }
}
