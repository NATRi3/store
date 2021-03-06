package by.epam.store.tag;

import by.epam.store.controller.command.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * The type Page tag.
 */
public class PageTag extends TagSupport {
    private static final String WEB_INF_PREFIX = "/WEB-INF/";

    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String uri = request.getRequestURI();
        if (!uri.contains(WEB_INF_PREFIX)) {
            HttpSession session = pageContext.getSession();
            session.setAttribute(SessionAttribute.PAGE, uri);
        }
        return SKIP_BODY;
    }
}
