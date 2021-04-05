package by.epam.store.model.service.impl;

import by.epam.store.annotation.Autowired;
import by.epam.store.annotation.Bean;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.dao.OrderDao;
import by.epam.store.model.entity.Cart;
import by.epam.store.model.entity.Order;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.User;
import by.epam.store.model.service.OrderService;
import by.epam.store.model.service.TypeSort;
import by.epam.store.model.validator.NumberValidator;
import by.epam.store.model.validator.TypeValidator;
import by.epam.store.model.validator.ValidationFields;
import by.epam.store.util.MessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * The type Base order service.
 */
@Bean
public class BaseOrderService implements OrderService {
    private final static Logger log = LogManager.getLogger(BaseOrderService.class);

    private OrderDao orderDao;

    /**
     * Instantiates a new Base order service.
     *
     * @param orderDao the order dao
     */
    public BaseOrderService(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    /**
     * Instantiates a new Base order service.
     */
    public BaseOrderService() {

    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public String createOrder(Map<String, String[]> parameters, User user, Cart cart) throws ServiceException {
        if (cart.getProducts().isEmpty() || cart.getProducts().containsValue(0)) {
            return MessageKey.ERROR_MESSAGE_EMPTY_CART;
        }
        if (!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.PHONE) ||
                !parameters.containsKey(RequestParameterAndAttribute.ADDRESS)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        try {
            String phone = parameters.get(RequestParameterAndAttribute.PHONE)[0];
            String address = parameters.get(RequestParameterAndAttribute.ADDRESS)[0];
            Map<Product, Integer> cartMap = cart.getProducts();
            List<Product> productList = cartMap.entrySet().stream()
                    .map(entry -> {
                        entry.getKey().setCountInOrder(entry.getValue());
                        return entry.getKey();
                    }).collect(Collectors.toList());
            Order order = Order.builder()
                    .id(generateIdLong())
                    .idUser(user.getId())
                    .phone(phone)
                    .price(cart.getTotalPrice())
                    .address(address)
                    .date(new Date())
                    .productList(productList)
                    .build();
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
        if (!NumberValidator.isLongValid(begin) &&
                !TypeValidator.isTypeOrderSort(sort)) {
            throw new ServiceException("Invalid params " + begin + " " + sort);
        }
        try {
            int beginPagination = Integer.parseInt(begin);
            TypeSort typeSort = TypeSort.valueOf(sort.toUpperCase());
            return orderDao.findOrdersByStatusAndSort(beginPagination, typeSort);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    private long generateIdLong() {
        return Math.abs(new Random().nextLong());
    }
}
