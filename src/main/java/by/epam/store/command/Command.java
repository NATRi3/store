package by.epam.store.command;

import by.epam.store.service.impl.*;

import javax.servlet.http.HttpServletRequest;

public interface Command extends BaseCommand{
    public String execute(HttpServletRequest request);
}
