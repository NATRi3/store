package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.util.PagePath;

import javax.servlet.http.HttpServletRequest;

public class NotFoundCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.PAGE_404;
    }
}
