package by.epam.store.controller;

import by.epam.store.command.Command;
import by.epam.store.command.CommandProvider;
import by.epam.store.command.TypeCommand;
import by.epam.store.pool.CustomConnectionPool;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
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

@WebServlet("/controller")
public class Controller extends HttpServlet {
    public static final Logger logger = LogManager.getLogger(Controller.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Optional<Command> command = CommandProvider.commandDefine(request.getParameter(RequestParameter.COMMAND));
        String page = command.orElse(TypeCommand.ERROR_NOT_FOUND.get()).execute(request);
        if(request.getSession(false)!=null){
            request.getSession().setAttribute(SessionAttribute.PAGE,page);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
        CustomConnectionPool.getInstance().closePool();
    }
}
