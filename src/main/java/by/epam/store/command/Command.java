package by.epam.store.command;

import by.epam.store.controller.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface Command.
 */
public interface Command {
    /**
     * Execute router.
     *
     * @param request the request
     * @return the router
     */
    Router execute(HttpServletRequest request);
}
