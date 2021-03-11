package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    public static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return Router.redirectTo(PagePath.LOGIN, request);
    }
}
