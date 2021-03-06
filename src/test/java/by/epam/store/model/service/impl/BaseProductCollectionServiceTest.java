package by.epam.store.model.service.impl;

import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.model.dao.impl.BaseCollectionDao;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.CollectionService;
import by.epam.store.util.MessageKey;
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
 * The type Base product collection service test.
 */
public class BaseProductCollectionServiceTest {
    private static final Logger log = LogManager.getLogger(BaseProductCollectionServiceTest.class);
    private CollectionService service;

    /**
     * Sets up.
     */
    @BeforeMethod
    public void setUp() {
        BaseCollectionDao dao = Mockito.mock(BaseCollectionDao.class);
        service = new BaseProductCollectionService(dao);
    }

    /**
     * Tear down.
     */
    @AfterMethod
    public void tearDown() {
    }

    /**
     * Test find all product collections by status.
     */
    @Test
    public void testFindAllProductCollectionsByStatus() {
        try {
            assertNotNull(service.findAllProductCollectionsByStatus("ACTIVE"));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test find all product collections.
     */
    @Test
    public void testFindAllProductCollections() {
        try {
            assertNotNull(service.findAllProductCollections());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test create collection.
     */
    @Test
    public void testCreateCollection() {
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put(RequestParameterAndAttribute.NAME_COLLECTION, new String[]{""});
        parameters.put(RequestParameterAndAttribute.INFO_COLLECTION, new String[]{""});
        try {
            assertEquals(service.createCollection(parameters), MessageKey.SUCCESSFUL_CREATE_COLLECTION);
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
        try {
            assertNotNull(service.changeStatus("1", TypeStatus.ACTIVE));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test change info.
     */
    @Test
    public void testChangeInfo() {
        Map<String, String[]> parameters = new HashMap<>();
        parameters.put(RequestParameterAndAttribute.ID_COLLECTION, new String[]{"1"});
        parameters.put(RequestParameterAndAttribute.INFO_COLLECTION, new String[]{""});
        try {
            assertEquals(service.changeInfo(parameters), MessageKey.SUCCESSFUL_CHANGE);
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }
}