package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Product;
import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.SessionAttribute;
import com.google.gson.Gson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GetShopListProductCommand implements CommandAsync {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        System.out.println(user.getId());
        List<Product> productList = new ArrayList<>();
/*        productList.add(new Product(1L,"NAME","INFO", BigDecimal.valueOf(1000), TypeStatus.ACTIVE,(byte) 10,"222"));
        productList.add(new Product(2L,"NAME","INFO", BigDecimal.valueOf(1222), TypeStatus.ACTIVE,(byte) 10,"222"));*/
        String json = new Gson().toJson(productList);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
