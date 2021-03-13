package by.epam.store.util;

import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseWriterUtil {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(ResponseWriterUtil.class);

    private ResponseWriterUtil() {
    }

    public static void writeTextToResponse(HttpServletRequest request, HttpServletResponse response, String messageKey) {
        try {
            String message = MessageCreator.getMessageFromBundleByLocale(messageKey, request);
            response.setContentType("application/text");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(message);
        } catch (IOException e) {
            log.error(e);
        }
    }

    public static void writeJsonToResponse(HttpServletResponse response, String json) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
