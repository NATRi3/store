package by.epam.store.service.impl;

import by.epam.store.entity.Cart;
import by.epam.store.entity.Order;
import by.epam.store.entity.Product;
import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.validator.FormValidator;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;
@Log4j2
public class OrderService implements by.epam.store.service.OrderService {
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
                Map<Long, Integer> orderMap = new HashMap<>();
                for (Map.Entry<Product, Integer> entry : cartMap.entrySet()) {
                    orderMap.put(entry.getKey().getId(), entry.getValue());
                }
                Order order = new Order(user.getId(), phone, address, cart.getTotalPrice(), orderMap);
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
}
