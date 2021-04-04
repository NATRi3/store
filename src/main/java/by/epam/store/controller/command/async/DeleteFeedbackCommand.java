package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.FeedbackService;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.annotation.Autowired;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The Delete feedback command.
 */
public class DeleteFeedbackCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(DeleteFeedbackCommand.class);
    private FeedbackService BASE_FEEDBACK_SERVICE;

    @Autowired
    public void setBASE_FEEDBACK_SERVICE(FeedbackService BASE_FEEDBACK_SERVICE) {
        this.BASE_FEEDBACK_SERVICE = BASE_FEEDBACK_SERVICE;
    }

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
