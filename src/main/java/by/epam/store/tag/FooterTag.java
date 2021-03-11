package by.epam.store.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class FooterTag extends TagSupport {
    private static final Logger log = LogManager.getLogger(FooterTag.class);
    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().print("<footer class='footer'><div class='container'>" +
                    "<span class='text-muted'>\t&#169;Copyright Sykhorykov A.D.</span>" +
                    "</div></footer>");
        } catch (IOException e) {
            log.error(e);
        }
        return SKIP_BODY;
    }
}
