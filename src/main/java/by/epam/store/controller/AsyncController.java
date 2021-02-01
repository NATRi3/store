package by.epam.store.controller;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.CommandProviderAsync;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Log4j2
@WebServlet("/async")
public class AsyncController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<CommandAsync> command = CommandProviderAsync.commandDefine(request.getParameter(RequestParameter.COMMAND));
        if(command.isPresent()) {
            command.get().execute(request, response);
        } else {
            response.sendError(404);
            //TODO 404error
        }

    }
}
