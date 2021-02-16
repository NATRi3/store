package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GetImageCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetImageCommand.class);
    public static final String IMAGE_NEWS_PATH = "C://projectImg//";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String imageName = request.getParameter(RequestParameter.IMAGE_NAME);
        String file = IMAGE_NEWS_PATH+imageName;
        try {
            byte[] image = Files.readAllBytes(Paths.get(file));
            response.setContentType("image/jpeg");
            response.setContentLength(image.length);
            response.getOutputStream().write(image);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
