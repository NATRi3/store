package by.epam.store.service.impl;

import by.epam.store.dao.impl.ProductDao;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.util.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class BaseProductServiceTest {
    private static final Logger log = LogManager.getLogger(BaseProductServiceTest.class);
    private ProductService service;

    @BeforeMethod
    public void setUp() {
        ProductDao dao = Mockito.mock(ProductDao.class);
        service = new BaseProductService(dao);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testFindProductById() {
        try {
            assertFalse(service.findProductById("1").isPresent());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testSaveProduct() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameterAndAttribute.ID_COLLECTION,"1");
        parameters.put(RequestParameterAndAttribute.PRICE_PRODUCT,"12.0");
        parameters.put(RequestParameterAndAttribute.NAME_PRODUCT,"");
        parameters.put(RequestParameterAndAttribute.INFO_PRODUCT,"");
        try {
            assertNotNull(service.saveProduct(parameters));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testChangeStatus() {
        try{
            assertNotNull(service.changeStatus("1", TypeStatus.ACTIVE));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testFindProductByCollectionAndSort() {
        try{
            assertTrue(service.findProductByCollectionAndSort("1","name","ACTIVE","10").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testFindRandomProduct() {
        try{
            assertTrue(service.findRandomProduct("1").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testChangeProduct() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameterAndAttribute.ID_COLLECTION,"1");
        parameters.put(RequestParameterAndAttribute.PRICE_PRODUCT,"12.0");
        parameters.put(RequestParameterAndAttribute.INFO_PRODUCT,"");
        try {
            assertNotNull(service.changeProduct(parameters));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testChangeImage() {
        try {
            assertNotNull(service.changeImage("1","parameters"));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testSearchProduct() {
        try{
            assertTrue(service.searchProduct("1").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }
}