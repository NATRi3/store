package by.epam.store.dao;

import by.epam.store.entity.Order;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.service.TypeSort;

import java.util.List;

/**
 * The interface Order dao.
 */
public interface OrderDao extends BaseDao<Order> {
    /**
     * Find user orders list.
     *
     * @param id the id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findUserOrders(long id) throws DaoException;

    /**
     * Find orders by status and sort list.
     *
     * @param beginPagination the begin pagination
     * @param typeSort        the type sort
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Order> findOrdersByStatusAndSort(int beginPagination, TypeSort typeSort) throws DaoException;
}
