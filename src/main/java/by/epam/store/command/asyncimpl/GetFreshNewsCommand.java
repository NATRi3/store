package by.epam.store.command.asyncimpl;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.News;
import by.epam.store.entity.Product;
import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@Log4j2
public class GetFreshNewsCommand implements CommandAsync {
    @SneakyThrows
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        List<News> productList = new ArrayList<>();
        String count = request.getParameter(RequestParameter.NEWS_AMOUNT);
        try {
            productList = newsService.getFreshNews(count);
            String json = new Gson().toJson(productList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (ServiceException | IOException e) {
            log.error(e);
            response.sendError(500);
        }
    }
}
