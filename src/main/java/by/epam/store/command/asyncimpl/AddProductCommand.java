package by.epam.store.command.async;

import by.epam.store.command.CommandAsync;
import by.epam.store.entity.Product;
import lombok.extern.log4j.Log4j2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Log4j2
public class AddProductCommand implements CommandAsync {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
/*        Product product = request.getParameter();
        productService.saveProduct();*/
    }
}
