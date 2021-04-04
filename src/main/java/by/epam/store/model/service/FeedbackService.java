package by.epam.store.model.service;

import by.epam.store.model.entity.Feedback;
import by.epam.store.model.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.annotation.Dependency;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Feedback service.
 */
@Dependency
public interface FeedbackService {

    /**
     * Gets feedback by id product.
     *
     * @param idProduct the id product
     * @return the feedback by id product
     * @throws ServiceException the service exception
     */
    List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException;

    /**
     * Create feedback optional.
     *
     * @param parameters the parameters
     * @param user       the user
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<String> createFeedback(Map<String, String[]> parameters, User user) throws ServiceException;

    /**
     * Delete feedback string.
     *
     * @param id the id
     * @return the string
     * @throws ServiceException the service exception
     */
    String deleteFeedback(String id) throws ServiceException;
}
