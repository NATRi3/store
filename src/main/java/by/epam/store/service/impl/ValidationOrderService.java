package by.epam.store.service.impl;

import by.epam.store.entity.Cart;
import by.epam.store.entity.Order;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.OrderService;
import by.epam.store.util.MessageKey;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;

import java.util.List;
import java.util.Map;

public class ValidationOrderService implements OrderService {

    private final BaseOrderService service;

    public ValidationOrderService(BaseOrderService service) {
        this.service = service;
    }

    @Override
    public String createOrder(Map<String, String> parameters, User user, Cart cart) throws ServiceException {
        if (cart.getProducts().isEmpty()) {
            return MessageKey.ERROR_MESSAGE_EMPTY_CART;
        }
        if (FormValidator.isFormValid(parameters)) {
            return service.createOrder(parameters, user, cart);
        } else {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
    }

    @Override
    public List<Order> findUserOrders(long id) throws ServiceException {
        return service.findUserOrders(id);
    }

    @Override
    public List<Order> findOrderList(String begin, String sort) throws ServiceException {
        if (!NumberValidator.isLongValid(begin) &&
                !TypeValidator.isTypeOrderSort(sort)) {
            throw new ServiceException("Invalid params " + begin + " " + sort);
        }
        return service.findOrderList(begin, sort);
    }
}
