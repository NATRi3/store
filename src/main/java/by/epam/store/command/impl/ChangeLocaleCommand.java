package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ChangeLocaleCommand implements Command {
    public static final Logger logger = LogManager.getLogger(ChangeLocaleCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String newLocale = request.getParameter(RequestParameter.NEW_LOCALE);
        session.setAttribute(SessionAttribute.LOCALE,newLocale);
        String page = request.getParameter(RequestParameter.CURRENT_PAGE).replace(request.getContextPath(),"");
        logger.info("Locale change");
        return page;
    }
}
