package by.epam.store.tag;

import by.epam.store.util.MessageCreator;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * The type Message tag.
 */
public class MessageTag extends TagSupport {
    private static final Logger log = LogManager.getLogger(MessageTag.class);

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String messageKey = (String) request.getAttribute(RequestParameterAndAttribute.MESSAGE);
        try {
            if (messageKey != null) {
                String message = MessageCreator.instance.getMessageFromBundleByLocale(messageKey, request);
                if (messageKey.contains("success")) {
                    pageContext.getOut().print(
                            "<div class='messages' style='position: fixed; top: 80px; right: 15px; width: 250px; z-index: 100;'>" +
                                    "<div id='my-alert-success' class='alert alert-success alert-dismissible fade show' role='alert'>" +
                                    "<br>" +
                                    message +
                                    "<br>" +
                                    "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                                    "<span aria-hidden='true'>×</span>" +
                                    "</button>" +
                                    "</div>" +
                                    "</div>");
                } else {
                    pageContext.getOut().print("<div class='messages' style='position: fixed; top: 80px; right: 15px; width: 250px; z-index: 100;'>" +
                            "<div id='my-alert-error' class='alert alert-danger alert-dismissible fade show' role='alert'>" +
                            "<br>" +
                            message +
                            "<br>" +
                            "<button type='button' class='close' data-dismiss='alert' aria-label='Close'>" +
                            "<span aria-hidden='true'>×</span>" +
                            "</button>" +
                            "</div>" +
                            "</div>");
                }
            }
        } catch (IOException e) {
            log.error(e);
        }
        return SKIP_BODY;
    }
}
