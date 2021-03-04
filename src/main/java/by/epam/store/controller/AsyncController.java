package by.epam.store.controller;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.CommandProviderAsync;
import by.epam.store.command.async.AddProductToCartCommand;
import by.epam.store.exception.CommandException;
import by.epam.store.util.RequestParameter;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/async")
public class AsyncController extends HttpServlet {
    private final static Logger log = LogManager.getLogger(AsyncController.class);

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
        try{
            if(command.isPresent()) {
                command.get().execute(request, response);
            } else {
                response.sendError(404);
            }
        } catch (CommandException e){
            log.error(e);
            response.sendError(500);
        }
    }
}
