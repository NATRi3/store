package by.epam.store.service.impl;

import by.epam.store.dao.impl.CollectionDao;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.CollectionService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.*;

public class BaseProductCollectionServiceTest {
    private static final Logger log = LogManager.getLogger(BaseProductCollectionServiceTest.class);
    private CollectionService service;

    @BeforeMethod
    public void setUp() {
        CollectionDao dao = Mockito.mock(CollectionDao.class);
        service = new BaseProductCollectionService(dao);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testFindAllProductCollectionsByStatus() {
        try {
            assertNotNull(service.findAllProductCollectionsByStatus("ACTIVE"));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testFindAllProductCollections() {
        try {
            assertNotNull(service.findAllProductCollections());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testCreateCollection() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameterAndAttribute.NAME_COLLECTION,"");
        parameters.put(RequestParameterAndAttribute.INFO_COLLECTION,"");
        try {
            assertEquals(service.createCollection(parameters), MessageKey.SUCCESSFUL_CREATE_COLLECTION);
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testChangeStatus() {
        try {
            assertNotNull(service.changeStatus("1", TypeStatus.ACTIVE));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testChangeInfo() {
        Map<String,String> parameters = new HashMap<>();
        parameters.put(RequestParameterAndAttribute.ID_COLLECTION,"1");
        parameters.put(RequestParameterAndAttribute.INFO_COLLECTION,"");
        try {
            assertEquals(service.changeInfo(parameters),MessageKey.SUCCESSFUL_CHANGE);
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }
}