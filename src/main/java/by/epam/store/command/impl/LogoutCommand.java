package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.util.PagePath;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    public static final Logger logger = LogManager.getLogger(LogoutCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String locale = session.getAttribute(SessionAttribute.LOCALE).toString();
        session.invalidate();
        return PagePath.LOGIN;
    }
}
