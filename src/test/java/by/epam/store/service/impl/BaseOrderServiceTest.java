package by.epam.store.service.impl;

import by.epam.store.dao.impl.BaseOrderDao;
import by.epam.store.entity.Cart;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.OrderService;
import by.epam.store.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.*;

import static org.testng.Assert.*;

/**
 * The type Base order service test.
 */
public class BaseOrderServiceTest {
    private static final Logger log = LogManager.getLogger(BaseFeedbackService.class);
    private OrderService service;

    /**
     * Sets up.
     */
    @BeforeMethod
    public void setUp() {
        BaseOrderDao mockito = Mockito.mock(BaseOrderDao.class);
        service = new BaseOrderService(mockito);
    }

    /**
     * Tear down.
     */
    @AfterMethod
    public void tearDown() {
    }

    /**
     * Test create order.
     */
    @Test
    public void testCreateOrder() {
        Map<String,String> params = new HashMap<>();
        Cart cart = new Cart();
        cart.setTotalPrice(BigDecimal.valueOf(0));
        User user = new User();
        user.setId(1);
        params.put(RequestParameterAndAttribute.PHONE,"1");
        params.put(RequestParameterAndAttribute.ADDRESS,"1");
        try {
            assertNotNull(service.createOrder(params,user,cart));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test find user orders.
     */
    @Test
    public void testFindUserOrders() {
        try {
            assertNotNull(service.findUserOrders(1));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test find order list.
     */
    @Test
    public void testFindOrderList() {
        try {
            assertNotNull(service.findOrderList("1","price"));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }
}