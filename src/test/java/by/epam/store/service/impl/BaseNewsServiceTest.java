package by.epam.store.service.impl;

import by.epam.store.dao.impl.BaseNewsDao;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.NewsService;
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

public class BaseNewsServiceTest {
    private static final Logger log = LogManager.getLogger(BaseNewsServiceTest.class);
    private NewsService service;

    @BeforeMethod
    public void setUp() {
        BaseNewsDao dao = Mockito.mock(BaseNewsDao.class);
        service = new BaseNewsService(dao);
    }

    @AfterMethod
    public void tearDown() {
    }

    @Test
    public void testFindFreshNews() {
        try {
            assertTrue(service.findFreshNews("3").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testFindSortNews() {
        try {
            assertTrue(service.findSortNews("title","0").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testDeleteNews() {
        try {
            assertFalse(service.deleteNews("3").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testCreateNews() {
        Map<String,String> params = new HashMap<>();
        params.put(RequestParameterAndAttribute.NEWS_TITLE,"");
        params.put(RequestParameterAndAttribute.NEWS_INFO,"");
        try {
            assertFalse(service.createNews(params).isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testChangeImage() {
        try {
            assertFalse(service.changeImage("3","string").isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    @Test
    public void testChangeNews() {
        Map<String,String> params = new HashMap<>();
        params.put(RequestParameterAndAttribute.ID_NEWS,"1");
        params.put(RequestParameterAndAttribute.NEWS_TITLE,"");
        params.put(RequestParameterAndAttribute.NEWS_INFO,"");
        try {
            assertFalse(service.changeNews(params).isEmpty());
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }
}