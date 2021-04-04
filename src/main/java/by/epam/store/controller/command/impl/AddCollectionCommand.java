package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.CollectionService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * The Add collection command.
 */
public class AddCollectionCommand implements Command {
    private static final Logger log = LogManager.getLogger(AddCollectionCommand.class);
    private CollectionService collectionService;

    @Autowired
    public void setCollectionService(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String[]> parameters = RequestUtil.getAllParametersFrom(request);
        try {
            String message = collectionService.createCollection(parameters);
            page = RouterResponseHelper.router(request, message, parameters, PagePath.ADMIN_PANEL_NEWS);
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
