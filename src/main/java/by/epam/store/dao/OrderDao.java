package by.epam.store.dao;

import by.epam.store.entity.Order;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.service.TypeSort;

import java.util.List;

public interface OrderDao {
    List<Order> getUserOrders(long id) throws DaoException;

    List<Order> getOrdersByStatusAndSort(int beginPagination, TypeSort typeSort, TypeStatus typeStatus) throws DaoException;
}
