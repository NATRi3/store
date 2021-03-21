package by.epam.store.service.impl;

import by.epam.store.dao.impl.BaseFeedbackDao;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.FeedbackService;
import by.epam.store.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.testng.Assert.*;

/**
 * The type Base feedback service test.
 */
public class BaseFeedbackServiceTest {
    private static final Logger log = LogManager.getLogger(BaseFeedbackService.class);
    private FeedbackService feedbackService;

    /**
     * Sets up.
     */
    @BeforeMethod
    public void setUp() {
        BaseFeedbackDao mockito = Mockito.mock(BaseFeedbackDao.class);
        feedbackService = new BaseFeedbackService(mockito);
    }

    /**
     * Tear down.
     */
    @AfterMethod
    public void tearDown() {

    }

    /**
     * Test get feedback by id product.
     */
    @Test
    public void testGetFeedbackByIdProduct() {
        try {
            assertTrue(feedbackService.getFeedbackByIdProduct("1").isEmpty());
        } catch (ServiceException e) {
            fail();
        }
    }

    /**
     * Test create feedback.
     */
    @Test
    public void testCreateFeedback() {
        Map<String, String> params = new HashMap<>();
        params.put(RequestParameterAndAttribute.EVALUATION,"2");
        params.put(RequestParameterAndAttribute.ID_PRODUCT,"1");
        User user = new User();
        try {
            Optional<String> expected = Optional.of("success");
            Mockito.when(feedbackService.createFeedback(params,user)).thenReturn(expected);
            Optional<String> actual = feedbackService.createFeedback(params,user);
            assertEquals(actual,expected);
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }

    /**
     * Test delete feedback.
     */
    @Test
    public void testDeleteFeedback() {
        try {
            assertNotNull(feedbackService.deleteFeedback("1"));
        } catch (ServiceException e) {
            log.error(e);
            fail();
        }
    }
}