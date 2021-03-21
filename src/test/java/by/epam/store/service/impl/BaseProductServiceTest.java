package by.epam.store.service.impl;

import by.epam.store.dao.impl.BaseProductDao;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * The type Base product service test.
 */
public class BaseProductServiceTest {
    private static final Logger log = LogManager.getLogger(BaseProductServiceTest.class);
    private ProductService service;

    /**
     * Sets up.
     */
    @BeforeMethod
    public void setUp() {
        BaseProductDao dao = Mockito.mock(BaseProductDao.class);
        service = new BaseProductService(dao);
    }

    /**
     * Tear down.
     */
    @AfterMethod
    public void tearDown() {
    }

    /**
     * Test find product by id.
     */
    @Test
    public void testFindProductById() {
        try {
            assertFalse(service.findProductById("1").isPresent());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test save product.
     */
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

    /**
     * Test change status.
     */
    @Test
    public void testChangeStatus() {
        try{
            assertNotNull(service.changeStatus("1", TypeStatus.ACTIVE));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test find product by collection and sort.
     */
    @Test
    public void testFindProductByCollectionAndSort() {
        try{
            assertTrue(service.findProductByCollectionAndSort("1","name","ACTIVE","10").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test find random product.
     */
    @Test
    public void testFindRandomProduct() {
        try{
            assertTrue(service.findRandomProduct("1").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test change product.
     */
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

    /**
     * Test change image.
     */
    @Test
    public void testChangeImage() {
        try {
            assertNotNull(service.changeImage("1","parameters"));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test search product.
     */
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