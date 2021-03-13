package by.epam.store.dao;

import by.epam.store.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public interface BaseDao<K> {
    Logger logger = LogManager.getLogger(BaseDao.class);

    List<K> findAll() throws DaoException;

    Optional<K> findEntityById(Long id) throws DaoException;

    boolean delete(Long id) throws DaoException;

    boolean update(K k) throws DaoException;

    K create(K k) throws DaoException;

    default void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }

    default void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
