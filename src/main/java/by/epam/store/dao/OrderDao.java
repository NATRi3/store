package by.epam.store.dao;

import by.epam.store.entity.Order;
import by.epam.store.exception.DaoException;

import java.util.List;

public interface OrderDao {
    List<Order> getUserOrders(long id) throws DaoException;
}
