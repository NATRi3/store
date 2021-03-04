package by.epam.store.command;

import by.epam.store.exception.CommandException;
import by.epam.store.service.impl.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface CommandAsync{
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
