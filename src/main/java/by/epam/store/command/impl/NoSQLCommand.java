package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.dao.impl.NoSQLDao;

import javax.servlet.http.HttpServletRequest;

public class NoSQLCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        request.setAttribute("Nosql", new NoSQLDao().getOb());
        return Router.forwardTo("/jsp/TestNoSQL.jsp");
    }
}
