package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ActivationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(ActivationCommand.class);
    @Override
    public String execute(HttpServletRequest request) {
        String code = request.getParameter(RequestParameter.ACTIVATION_CODE);
        try {
            userService.activate(code);
        } catch (ServiceException e) {
            logger.info(e.getMessage());
        }
        return PagePath.ACTIVATION_PAGE;
    }
}
