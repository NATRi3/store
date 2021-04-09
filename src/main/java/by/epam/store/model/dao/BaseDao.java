package by.epam.store.model.dao;

import by.epam.store.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

/**
 * The interface Base dao.
 *
 * @param <K> the type parameter
 */
public interface BaseDao<K> {
    /**
     * The constant logger.
     */
    Logger logger = LogManager.getLogger(BaseDao.class);

    /**
     * Find all list.
     *
     * @return the list
     * @throws DaoException the dao exception
     */
    List<K> findAll() throws DaoException;

    /**
     * Find entity by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<K> findEntityById(Long id) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(Long id) throws DaoException;

    /**
     * Update boolean.
     *
     * @param k the k
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean update(K k) throws DaoException;

    /**
     * Create k.
     *
     * @param k the k
     * @return the k
     * @throws DaoException the dao exception
     */
    K create(K k) throws DaoException;

    /**
     * Close.
     *
     * @param statement the statement
     */
    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    /**
     * Close.
     *
     * @param connection the connection
     */
    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    default void rollback(Connection connection){
        try {
            if (connection != null) {
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
