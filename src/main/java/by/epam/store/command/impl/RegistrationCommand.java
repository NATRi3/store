package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.UserService;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class RegistrationCommand implements Command {
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private static final UserService userService = ServiceCreator.getInstance().getUserService();
    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String ,String> parameters = new HashMap<>();
        parameters.put(RequestParameter.NAME,request.getParameter(RequestParameter.NAME));
        parameters.put(RequestParameter.EMAIL, request.getParameter(RequestParameter.EMAIL));
        parameters.put(RequestParameter.PASSWORD, request.getParameter(RequestParameter.PASSWORD));
        parameters.put(RequestParameter.REPEAT_PASSWORD, request.getParameter(RequestParameter.REPEAT_PASSWORD));
        try {
            Optional<String> optionalMessage = userService.registerClient(parameters);
            if(optionalMessage.isPresent()){
                request.setAttribute(RequestParameter.MESSAGE, optionalMessage.get());
                page = Router.forwardTo(PagePath.REGISTRATION);
                for(Map.Entry<String,String> entry: parameters.entrySet()){
                    request.setAttribute(entry.getKey(),entry.getValue());
                }
            }else {
                page = Router.redirectTo(PagePath.LOGIN);
            }
        } catch (ServiceException e) {
            logger.info(e);
            page = Router.redirectTo(PagePath.PAGE_500);
        }
        return page;
    }
}
