package by.epam.store.dao;

import by.epam.store.entity.Feedback;
import by.epam.store.exception.DaoException;

import java.util.List;

public interface FeedbackDao {
    List<Feedback> findAllByProductId(long id) throws DaoException;
}
