package by.epam.store.service;

import by.epam.store.entity.Feedback;
import by.epam.store.exception.ServiceException;

import java.util.List;

public interface FeedbackService {
    List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException;
}
