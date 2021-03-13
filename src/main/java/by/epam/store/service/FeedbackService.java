package by.epam.store.service;

import by.epam.store.dao.DaoCreator;
import by.epam.store.dao.impl.FeedbackDao;
import by.epam.store.entity.Feedback;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface FeedbackService {
    FeedbackDao feedbackDao = DaoCreator.getInstance().getFeedbackDao();

    List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException;

    Optional<String> createFeedback(Map<String, String> parameters, User user) throws ServiceException;

    String deleteFeedback(String id) throws ServiceException;
}
