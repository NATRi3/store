package by.epam.store.command.asyncimpl;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Feedback;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.RequestParameter;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Log4j2
public class GetListProductFeedbackCommand implements CommandAsync {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter(RequestParameter.ID_PRODUCT);
        try {
            List<Feedback> feedbacks = feedbackService.getFeedbackByIdProduct(id);
            String json = new Gson().toJson(feedbacks);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (ServiceException e) {
            log.error(e);//todo
        } catch (IOException e) {
            log.error(e);
        }
    }
}
