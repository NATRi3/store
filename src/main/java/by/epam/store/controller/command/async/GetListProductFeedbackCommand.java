package by.epam.store.controller.command.async;

import by.epam.store.controller.command.CommandAsync;
import by.epam.store.model.entity.Feedback;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.FeedbackService;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.annotation.Autowired;
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
    private FeedbackService BASE_FEEDBACK_SERVICE;
    @Autowired
    public void setBASE_FEEDBACK_SERVICE(FeedbackService BASE_FEEDBACK_SERVICE) {
        this.BASE_FEEDBACK_SERVICE = BASE_FEEDBACK_SERVICE;
    }

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
