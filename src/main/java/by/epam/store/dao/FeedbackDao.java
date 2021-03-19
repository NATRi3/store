package by.epam.store.dao;

import by.epam.store.entity.Feedback;
import by.epam.store.exception.DaoException;

import java.util.List;

/**
 * The interface Feedback dao.
 */
public interface FeedbackDao extends BaseDao<Feedback> {
    /**
     * Find all by product id list.
     *
     * @param id the id
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Feedback> findAllByProductId(long id) throws DaoException;
}
