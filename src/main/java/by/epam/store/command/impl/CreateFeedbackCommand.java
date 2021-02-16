package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.command.TypeCommand;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.FeedbackService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreateFeedbackCommand implements Command {
    private final static Logger log = LogManager.getLogger(CreateFeedbackCommand.class);
    private static final FeedbackService feedbackService = ServiceCreator.getInstance().getFeedbackService();
    @Override
    public String execute(HttpServletRequest request) {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameter.FEEDBACK,request.getParameter(RequestParameter.FEEDBACK));
        parameters.put(RequestParameter.EVALUATION, request.getParameter(RequestParameter.EVALUATION));
        parameters.put(RequestParameter.ID_PRODUCT,request.getParameter(RequestParameter.ID_PRODUCT));
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            Optional<String> optionalMessage = feedbackService.createFeedback(parameters,user);
            optionalMessage.ifPresent(s -> request.setAttribute(RequestParameter.MESSAGE, s));
            for(Map.Entry<String,String> entry: parameters.entrySet()){
                request.setAttribute(entry.getKey(),entry.getValue());
            }
            return TypeCommand.REDIRECT_TO_SINGLE_PRODUCT.get().execute(request);
        } catch (ServiceException e) {
            log.error(e);
            return PagePath.PAGE_500;
        }
    }
}
