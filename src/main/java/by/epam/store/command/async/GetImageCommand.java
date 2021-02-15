package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Log4j2
public class GetImageCommand implements CommandAsync {
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
