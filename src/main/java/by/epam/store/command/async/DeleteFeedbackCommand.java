package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.command.ServiceCreator;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.FeedbackService;
import by.epam.store.util.MessageCreator;
import by.epam.store.util.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteFeedbackCommand implements CommandAsync {
    private static final Logger log = LogManager.getLogger(DeleteFeedbackCommand.class);
    private static final FeedbackService feedbackService = ServiceCreator.getInstance().getFeedbackService();
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            String idFeedback = request.getParameter(RequestParameter.ID_FEEDBACK);
            try {
                String messageKey = feedbackService.deleteFeedback(idFeedback);
                String message = MessageCreator.getMessageFromBundleByLocale(messageKey, request);
                response.setContentType("application/text");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(message);
            } catch (ServiceException e) {
                log.error(e);
                response.sendError(500);
            }
        } catch (IOException e){
            log.error(e);
        }
    }
}
