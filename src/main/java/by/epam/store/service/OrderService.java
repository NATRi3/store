package by.epam.store.service;

import by.epam.store.dao.DaoCreator;
import by.epam.store.dao.impl.OrderDao;
import by.epam.store.entity.Cart;
import by.epam.store.entity.Order;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface OrderService {

    String createOrder(Map<String, String> parameters, User user, Cart cart) throws ServiceException;

    List<Order> findUserOrders(long id) throws ServiceException;

    List<Order> findOrderList(String begin, String sort, String status) throws ServiceException;
}
