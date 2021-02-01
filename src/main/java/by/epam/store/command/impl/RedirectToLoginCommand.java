package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.util.PagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToLoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        String page = PagePath.LOGIN_PAGE;
        return page;
    }
}
