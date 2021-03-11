package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductCollectionService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.RouterResponseHelper;
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
        Router page;
        Map<String,String> parameters = RequestUtil.getAllParametersFrom(request);
        try {
            Optional<String> optionalMessage = collectionService.createCollection(parameters);
            page = RouterResponseHelper.router(request,optionalMessage,parameters,PagePath.ADMIN_PANEL_NEWS);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
