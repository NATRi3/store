package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductCollectionService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AddCollectionCommand implements Command {
    private static final Logger log = LogManager.getLogger(AddCollectionCommand.class);
    private static final ProductCollectionService collectionService = ServiceCreator.getInstance().getCollectionService();
    @Override
    public Router execute(HttpServletRequest request) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(RequestParameter.NAME_COLLECTION,request.getParameter(RequestParameter.NAME_COLLECTION));
        parameters.put(RequestParameter.INFO_COLLECTION,request.getParameter(RequestParameter.INFO_COLLECTION));
        try{
            Optional<String> optionalMessage = collectionService.createCollection(parameters);
            optionalMessage.ifPresent(s -> request.setAttribute(RequestParameter.MESSAGE,s));
            for(Map.Entry<String,String> entry: parameters.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
            return Router.forwardTo(PagePath.ADMIN_PANEL_COLLECTION,request);
        } catch (ServiceException e) {
            log.error(e);
            return Router.redirectTo(PagePath.PAGE_500,request);
        }
    }
}
