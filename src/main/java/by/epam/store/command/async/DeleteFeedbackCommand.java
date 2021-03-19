package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.FeedbackService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Delete feedback command.
 */
public class DeleteFeedbackCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(DeleteFeedbackCommand.class);
    private static final FeedbackService BASE_FEEDBACK_SERVICE = ServiceCreator.getInstance().getFeedbackService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idFeedback = request.getParameter(RequestParameterAndAttribute.ID_FEEDBACK);
        try {
            String messageKey = BASE_FEEDBACK_SERVICE.deleteFeedback(idFeedback);
            ResponseWriterUtil.writeTextToResponse(request, response, messageKey);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
