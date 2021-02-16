package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.Order;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class OrderDao implements by.epam.store.dao.OrderDao, BaseDao<Order> {
    private final static Logger log = LogManager.getLogger(OrderDao.class);
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_CREATE_ORDER = "INSERT INTO l4tsmab3ywpoc8m0.orders SET id_account = ?, price = ?, phone=?, adress=?";
    private static final String SQL_CREATE_ORDER_PRODUCT = "INSERT INTO l4tsmab3ywpoc8m0.order_product SET id_order = ?, id_product = ?, product_amount=?";

    @Override
    public List<Order> findAll() throws DaoException {
        return null;
    }

    @Override
    public Optional<Order> findEntityById(Long aLong) throws DaoException {
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
        Connection connection = null;
        PreparedStatement statementCreateOrder = null;
        PreparedStatement statementCreateOrderProduct = null;
        try {
            try {
                connection = connection();
                connection.setAutoCommit(false);
                statementCreateOrder = connection.prepareStatement(SQL_CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
                statementCreateOrderProduct = connection.prepareStatement(SQL_CREATE_ORDER_PRODUCT);
                statementCreateOrder.setLong(1, order.getIdUser());
                statementCreateOrder.setBigDecimal(2, order.getPrice());
                statementCreateOrder.setString(3, order.getPhone());
                statementCreateOrder.setString(4, order.getAddress());
                statementCreateOrder.executeUpdate();
                ResultSet resultSet = statementCreateOrder.getGeneratedKeys();
                if (resultSet.next()) {
                    order.setId(resultSet.getLong(1));
                }
                for (Map.Entry<Long, Integer> product : order.getProduct().entrySet()) {
                    statementCreateOrderProduct.setLong(1, order.getId());
                    statementCreateOrderProduct.setLong(2, product.getKey());
                    statementCreateOrderProduct.setInt(3, product.getValue());
                    statementCreateOrderProduct.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                connection.rollback();
                log.error(e);
                throw new DaoException(e);
            } finally {
                assert connection != null;
                connection.setAutoCommit(true);
            }
        } catch (SQLException e){
            log.error(e);
            throw new DaoException(e);
        } finally {
            close(statementCreateOrder);
            close(statementCreateOrderProduct);
            close(connection);
        }
        return order;
    }
    private Connection connection() throws SQLException {
        return connectionPool.getConnection();
    }

}
