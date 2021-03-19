package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Feedback;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.FeedbackService;
import by.epam.store.service.ServiceCreator;
import by.epam.store.command.RequestParameterAndAttribute;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The type Get list product feedback command.
 */
public class GetListProductFeedbackCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListProductFeedbackCommand.class);
    private static final FeedbackService BASE_FEEDBACK_SERVICE = ServiceCreator.getInstance().getFeedbackService();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String id = request.getParameter(RequestParameterAndAttribute.ID_PRODUCT);
        try {
            List<Feedback> feedbacks = BASE_FEEDBACK_SERVICE.getFeedbackByIdProduct(id);
            String json = new Gson().toJson(feedbacks);
            ResponseWriterUtil.writeJsonToResponse(response, json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
