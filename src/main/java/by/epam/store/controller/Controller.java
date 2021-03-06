package by.epam.store.controller;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.command.CommandProvider;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * The type Controller.
 */
@WebServlet("/controller")
public class Controller extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(Controller.class);


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String commandName = request.getParameter(RequestParameterAndAttribute.COMMAND);
        Optional<Command> optionalCommand = CommandProvider.commandDefine(commandName);
        Command command = optionalCommand.get();
        Router router = command.execute(request);
        setPageToSession(request, router.getPage());
        if (router.isRedirect()) {
            response.sendRedirect(router.getPage());
        } else {
            RequestDispatcher dispatcher = request.getRequestDispatcher(router.getPage());
            dispatcher.forward(request, response);
        }
    }

    private void setPageToSession(HttpServletRequest request, String page) {
        if (request.getSession(false) != null) {
            if (page.contains("/WEB-INF/")) {
                request.getSession().setAttribute(SessionAttribute.PAGE, request.getRequestURI() + "?" + request.getQueryString());
            } else {
                request.getSession().setAttribute(SessionAttribute.PAGE, page);
            }
        }
    }
}
