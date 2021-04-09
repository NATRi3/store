package by.epam.store.model.service;

import by.epam.store.model.entity.Cart;
import by.epam.store.model.entity.Order;
import by.epam.store.model.entity.User;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * The interface Order service.
 */
public interface OrderService {

    /**
     * Create order string.
     *
     * @param parameters the parameters
     * @param user       the user
     * @param cart       the cart
     * @return the string
     * @throws ServiceException the service exception
     */
    String createOrder(Map<String, String[]> parameters, User user, Cart cart) throws ServiceException;

    /**
     * Find user orders list.
     *
     * @param id the id
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findUserOrders(long id) throws ServiceException;

    /**
     * Find order list list.
     *
     * @param begin the begin
     * @param sort  the sort
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Order> findOrderList(String begin, String sort) throws ServiceException;
}
