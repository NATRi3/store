package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.util.PagePath;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RedirectToCartCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.CART_PAGE;
    }
}
