package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.listner.SessionListener;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.PagePath;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedirectToAccountCommand implements Command {
    public static final Logger logger = LogManager.getLogger(RedirectToAccountCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            user = userService.findUserById(user.getId());
            return PagePath.ACCOUNT_PAGE;
        } catch (ServiceException e) {
            logger.error(e);
            return PagePath.PAGE_500;
        }
    }
}
