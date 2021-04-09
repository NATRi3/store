package by.epam.store.model.dao;

import by.epam.store.model.entity.ProductCollection;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.DaoException;

import java.util.List;

/**
 * The interface Collection dao.
 */
public interface CollectionDao extends BaseDao<ProductCollection> {
    /**
     * Find collections by status.
     *
     * @param typeStatus the type status
     * @return the list
     * @throws DaoException the dao exception
     */
    List<ProductCollection> findByStatus(TypeStatus typeStatus) throws DaoException;

    /**
     * Update status.
     *
     * @param idCollection the id collection
     * @param status       the new status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateStatus(long idCollection, TypeStatus status) throws DaoException;

    /**
     * Update info.
     *
     * @param id   the id
     * @param info the new info
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean updateInfo(Long id, String info) throws DaoException;
}
