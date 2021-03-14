package by.epam.store.service.impl;

import by.epam.store.dao.impl.FeedbackDao;
import by.epam.store.entity.Feedback;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.FeedbackService;
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

public class BaseFeedbackServiceTest {
    private static final Logger log = LogManager.getLogger(BaseFeedbackService.class);
    private FeedbackService feedbackService;

    @BeforeMethod
    public void setUp() {
        FeedbackDao mockito = Mockito.mock(FeedbackDao.class);
        feedbackService = new BaseFeedbackService(mockito);
    }

    @AfterMethod
    public void tearDown() {

    }

    @Test
    public void testGetFeedbackByIdProduct() {
        try {
            assertTrue(feedbackService.getFeedbackByIdProduct("1").isEmpty());
        } catch (ServiceException e) {
            fail();
        }
    }

    @Test
    public void testCreateFeedback() {
        Map<String, String> params = new HashMap<>();
        params.put(RequestParameterAndAttribute.EVALUATION,"2");
        params.put(RequestParameterAndAttribute.ID_PRODUCT,"1");
        User user = new User();
        try {
            assertFalse(feedbackService.createFeedback(params,user).isPresent());
        } catch (ServiceException e) {
            fail();
        }
    }

    @Test
    public void testDeleteFeedback() {
        try {
            assertNotNull(feedbackService.deleteFeedback("1"));
        } catch (ServiceException e) {
            fail();
        }
    }
}