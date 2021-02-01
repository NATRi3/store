package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.entity.Product;
import by.epam.store.util.RequestParameter;
import by.epam.store.util.PagePath;
import com.google.gson.Gson;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class RedirectToShopCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.SHOP_PAGE;
    }
}
