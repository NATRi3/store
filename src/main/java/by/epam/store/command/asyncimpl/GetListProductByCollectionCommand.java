package by.epam.store.command.asyncimpl;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Product;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.RequestParameter;
import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class GetListProductByCollectionCommand implements CommandAsync {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        String idCollection = request.getParameter(RequestParameter.ID_COLLECTION);
        String typeSort = request.getParameter(RequestParameter.TYPE_SORT);
        String begin = request.getParameter(RequestParameter.BEGIN_PAGINATION);
        String status = request.getParameter(RequestParameter.TYPE_STATUS);
        try {
            List<Product> productList = productService.findProductByCollectionAndSort(idCollection,typeSort,status,begin);
            String json = new Gson().toJson(productList);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
        } catch (ServiceException | IOException e) {
            log.error(e);
        }
    }
}
