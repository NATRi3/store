package by.epam.store.dao;

import by.epam.store.entity.Order;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.service.TypeSort;

import java.util.List;

public interface OrderDao extends BaseDao<Order> {
    List<Order> findUserOrders(long id) throws DaoException;

    List<Order> findOrdersByStatusAndSort(int beginPagination, TypeSort typeSort) throws DaoException;
}
