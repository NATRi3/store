package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.ProductCollectionService;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.ResponseWriterUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BlockCollectionCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(BlockProductCommand.class);
    private static final ProductCollectionService collectionService = ServiceCreator.getInstance().getCollectionService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            String id = request.getParameter(RequestParameterAndAttribute.ID_COLLECTION);
            String messageKey = collectionService.changeStatus(id, TypeStatus.BLOCKED);
            ResponseWriterUtil.writeTextToResponse(request, response, messageKey);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
