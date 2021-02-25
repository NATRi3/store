package by.epam.store.service.impl;

import by.epam.store.entity.Cart;
import by.epam.store.entity.Order;
import by.epam.store.entity.Product;
import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.TypeSort;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService implements by.epam.store.service.OrderService {
    private final static Logger log = LogManager.getLogger(OrderService.class);
    @Override
    public String createOrder(Map<String, String> parameters, User user, Cart cart) throws ServiceException {
        try {
            if(cart.getProducts().isEmpty()){
                return MessageKey.ERROR_MESSAGE_EMPTY_CART;
            }
            if(FormValidator.isFormValid(parameters)) {
                String phone = parameters.get(RequestParameter.PHONE);
                String address = parameters.get(RequestParameter.ADDRESS);
                Map<Product, Integer> cartMap = cart.getProducts();
                Map<Product, Integer> orderMap = new HashMap<>();
                for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
                    orderMap.put(entry.getKey(), entry.getValue());
                }
                Order order = new Order(user.getId(), phone, address, cart.getTotalPrice(), orderMap, new Date());
                orderDao.create(order);
                cart.clear();
                return MessageKey.SUCCESSFUL_CREATE_ORDER;
            } else {
                return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException();
        }
    }

    @Override
    public List<Order> getUserOrders(long id) throws ServiceException {
        try {
            List<Order> orders = orderDao.getUserOrders(id);
            return orders;
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getOrderList(String begin, String sort, String status) throws ServiceException {
        if(!NumberValidator.isLongValid(begin)&&
            !TypeValidator.isTypeOrderSort(sort)&&
            !TypeValidator.isTypeOrderStatus(status)){
            throw new ServiceException("Invalid params "+ begin + " " + sort + " " + status);
        }
        try {
            int beginPagination = Integer.parseInt(begin);
            TypeSort typeSort = TypeSort.valueOf(sort.toUpperCase());
            TypeStatus typeStatus = TypeStatus.valueOf(status.toUpperCase());
            return orderDao.getOrdersByStatusAndSort(beginPagination,typeSort,typeStatus);
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e);
        }
    }
}
