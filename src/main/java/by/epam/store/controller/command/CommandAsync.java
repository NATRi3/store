package by.epam.store.controller.command;

import by.epam.store.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The interface Command async.
 */
public interface CommandAsync {
    /**
     * Execute.
     *
     * @param request  the request
     * @param response the response
     * @throws CommandException the command exception
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
