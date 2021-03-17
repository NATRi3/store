package by.epam.store.tag;

import by.epam.store.util.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PageTag extends TagSupport {
    @Override
    public int doStartTag() throws JspException {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String uri = request.getRequestURI();
        HttpSession session = pageContext.getSession();
        session.setAttribute(SessionAttribute.PAGE,uri);
        return SKIP_BODY;
    }
}
