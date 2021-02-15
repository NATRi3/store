package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import by.epam.store.validator.LocalValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Log4j2
public class ChangeLocaleCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String newLocale = request.getParameter(RequestParameter.NEW_LOCALE);
        if(LocalValidator.isLocale(newLocale)) {
            session.setAttribute(SessionAttribute.LOCALE, newLocale);
            String page = request.getParameter(RequestParameter.CURRENT_PAGE).replace(request.getContextPath(), "");
            log.info("Locale change");
            return page;
        } else {
            log.error("Local not found " + newLocale);
            return PagePath.PAGE_404;
        }
    }
}
