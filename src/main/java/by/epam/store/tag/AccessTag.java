package by.epam.store.tag;

import by.epam.store.entity.User;
import by.epam.store.util.SessionAttribute;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class AccessTag extends TagSupport {
    private String access;

    public void setAccess(String access) {
        this.access = access;
    }

    @Override
    public int doStartTag() throws JspException {
        HttpSession session = pageContext.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        if (user.getRole().name().equalsIgnoreCase(access)) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
