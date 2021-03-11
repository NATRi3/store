package by.epam.store.command;

import by.epam.store.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandAsync {
    void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
