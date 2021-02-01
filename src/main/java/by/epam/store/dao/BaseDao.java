package by.epam.store.dao;

import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;

public interface BaseDao<K> {
    Logger logger = LogManager.getLogger(BaseDao.class);
    List<K> findAll() throws DaoException;
    Optional<K> findEntityById (Long id) throws DaoException;
    boolean delete (Long id) throws DaoException;
    K update(K k) throws DaoException;
    K create (K k) throws DaoException;
}