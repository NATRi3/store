package by.epam.store.dao.impl;

import by.epam.store.dao.OrderDao;
import by.epam.store.entity.Order;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.NoSQLConnectionPool;
import by.epam.store.service.TypeSort;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NoSQLDao implements OrderDao {
    private static final Logger log = LogManager.getLogger(NoSQLDao.class);
    private final NoSQLConnectionPool connectionPool;

    public NoSQLDao(){
        connectionPool = NoSQLConnectionPool.getInstance();
    }

    public NoSQLDao(NoSQLConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findEntityById(Long id) throws DaoException {
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Order order) throws DaoException {
        return false;
    }

    @Override
    public Order create(Order order) throws DaoException {
        try {
            connectionPool.getOrderCollection().insertOne(order);
            return order;
        } catch (MongoException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findUserOrders(long id) throws DaoException {
        try {
            List<Order> result = new ArrayList<>();
            BasicDBObject query = new BasicDBObject("idUser",id);
            for (Order order : connectionPool.getOrderCollection().find(query)) {
                result.add(order);
            }
            System.out.println(result);
            return result;
        } catch (MongoException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findOrdersByStatusAndSort(int beginPagination, TypeSort typeSort) throws DaoException {
        try {
            List<Order> result = new ArrayList<>();
            BasicDBObject query = new BasicDBObject(typeSort.getString(),(typeSort.isDesc()?-1:1));
            for (Order order : connectionPool.getOrderCollection().find(query)) {
                result.add(order);
            }
            return result;
        } catch (MongoException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }
}
