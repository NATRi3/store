package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import by.epam.store.model.validator.LocalValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * The Change locale command.
 */
public class ChangeLocaleCommand implements Command {
    private final static Logger log = LogManager.getLogger(ChangeLocaleCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String newLocale = request.getParameter(RequestParameterAndAttribute.NEW_LOCALE);
        if (LocalValidator.isLocale(newLocale)) {
            session.setAttribute(SessionAttribute.LOCALE, newLocale);
            String page = (String) request.getSession().getAttribute(SessionAttribute.PAGE);
            log.info("Locale change");
            return Router.redirectTo(page, request);
        } else {
            log.error("Local not found " + newLocale);
            return Router.redirectTo(PagePath.PAGE_404, request);
        }
    }
}
