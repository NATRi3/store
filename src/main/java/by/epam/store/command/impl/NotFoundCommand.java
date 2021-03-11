package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.util.PagePath;

import javax.servlet.http.HttpServletRequest;

public class NotFoundCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return Router.redirectTo(PagePath.PAGE_404, request);
    }
}
