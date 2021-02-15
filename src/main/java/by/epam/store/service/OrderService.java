package by.epam.store.service;

import by.epam.store.dao.impl.OrderDao;
import by.epam.store.entity.Cart;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;

import java.util.Map;

public interface OrderService {
    static final OrderDao orderDao = DaoCreator.getInstance().getOrderDao();
    String createOrder(Map<String, String> parameters, User user, Cart cart) throws ServiceException;
}
