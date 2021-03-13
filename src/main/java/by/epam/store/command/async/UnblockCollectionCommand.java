package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.CollectionService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.ResponseWriterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnblockCollectionCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(UnblockCollectionCommand.class);
    private static final CollectionService collectionService = ServiceCreator.getInstance().getCollectionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String id = request.getParameter(RequestParameterAndAttribute.ID_COLLECTION);
            String messageKey = collectionService.changeStatus(id, TypeStatus.ACTIVE);
            ResponseWriterUtil.writeTextToResponse(request, response, messageKey);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
