package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Product;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameter;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
@Log4j2
public class GetProduct implements CommandAsync {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String gender = request.getParameter(RequestParameter.GENDER);
        String typeCloth = request.getParameter(RequestParameter.TYPE_CLOTH);
        List<Product> productList = productService.findProductByGenderAndType(gender, typeCloth);
        String json = new Gson().toJson(productList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            log.error(e);
        }
    }
}
