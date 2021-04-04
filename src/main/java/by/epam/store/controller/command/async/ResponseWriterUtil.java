package by.epam.store.controller.command.async;

import by.epam.store.util.MessageCreator;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Response writer util.
 */
public class ResponseWriterUtil {

    private static final Logger log = org.apache.logging.log4j.LogManager.getLogger(ResponseWriterUtil.class);

    private ResponseWriterUtil() {
    }

    /**
     * Write text to response.
     *
     * @param request    the request
     * @param response   the response
     * @param messageKey the message key
     */
    static void writeTextToResponse(HttpServletRequest request, HttpServletResponse response, String messageKey) {
        try {
            String message = MessageCreator.instance.getMessageFromBundleByLocale(messageKey, request);
            response.setContentType("application/text");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(message);
        } catch (IOException e) {
            log.error(e);
        }
    }

    /**
     * Write json to response.
     *
     * @param response the response
     * @param json     the json
     */
    static void writeJsonToResponse(HttpServletResponse response, String json) {
        try {
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
