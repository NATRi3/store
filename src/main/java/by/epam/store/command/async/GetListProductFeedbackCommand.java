package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.entity.Feedback;
import by.epam.store.exception.CommandException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.FeedbackService;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.ResponseWriterUtil;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GetListProductFeedbackCommand implements CommandAsync {
    private final static Logger log = LogManager.getLogger(GetListProductFeedbackCommand.class);
    public static final FeedbackService feedbackService = ServiceCreator.getInstance().getFeedbackService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String id = request.getParameter(RequestParameter.ID_PRODUCT);
        try {
            List<Feedback> feedbacks = feedbackService.getFeedbackByIdProduct(id);
            String json = new Gson().toJson(feedbacks);
            ResponseWriterUtil.writeJsonToResponse(response,json);
        } catch (ServiceException e) {
            log.error(e);
            throw new CommandException(e);
        }
    }
}
