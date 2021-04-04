package by.epam.store.model.dao;

import by.epam.store.model.entity.Order;
import by.epam.store.exception.DaoException;
import by.epam.store.model.service.TypeSort;
import by.epam.store.annotation.Dependency;

import java.util.List;

/**
 * The interface Order dao.
 */
@Dependency
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
