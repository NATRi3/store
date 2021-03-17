package by.epam.store.service.impl;

import by.epam.store.dao.DaoCreator;
import by.epam.store.dao.OrderDao;
import by.epam.store.entity.*;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.OrderService;
import by.epam.store.service.TypeSort;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class BaseOrderService implements OrderService {
    private final static Logger log = LogManager.getLogger(BaseOrderService.class);
    private final OrderDao orderDao;

    public BaseOrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public BaseOrderService() {
        orderDao = DaoCreator.getInstance().getNoSQLOrderDao();
    }

    @Override
    public String createOrder(Map<String, String> parameters, User user, Cart cart) throws ServiceException {
        try {
            String phone = parameters.get(RequestParameterAndAttribute.PHONE);
            String address = parameters.get(RequestParameterAndAttribute.ADDRESS);
            Map<Product, Integer> cartMap = cart.getProducts();
            List<Product> productList = new ArrayList<>();
            for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
                Product product = entry.getKey();
                product.setCountInOrder(entry.getValue());
                productList.add(product);
            }
            Order order = new Order(generateIdLong(), user.getId(), phone, address, cart.getTotalPrice(), new Date(), productList);
            orderDao.create(order);
            cart.clear();
            return MessageKey.SUCCESSFUL_CREATE_ORDER;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Order> findUserOrders(long id) throws ServiceException {
        try {
            return orderDao.findUserOrders(id);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> findOrderList(String begin, String sort) throws ServiceException {
        try {
            int beginPagination = Integer.parseInt(begin);
            TypeSort typeSort = TypeSort.valueOf(sort.toUpperCase());
            return orderDao.findOrdersByStatusAndSort(beginPagination, typeSort);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    private long generateIdLong(){
        return Math.abs(new Random().nextLong());
    }
}
