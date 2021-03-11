package by.epam.store.command;

import by.epam.store.controller.Router;

import javax.servlet.http.HttpServletRequest;

public interface Command {
    Router execute(HttpServletRequest request);
}
