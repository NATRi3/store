package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.Order;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class OrderDao implements BaseDao<Order> {
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_CREATE_ORDER = "INSERT INTO orders SET id_account = ? AND price = ?";
    private static final String SQL_CREATE_ORDER_PRODUCT = "INSERT INTO order_product SET id_order = ? AND id_product = ? AND product_amount=?";
    private static final String SQL_SELECT_ID_BY_ALL = "SELECT id_orders FROM orders WHERE id_account=? AND price=?";

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
        try{
            connection = connection();
            PreparedStatement statementCreateOrder = connection.prepareStatement(SQL_CREATE_ORDER);
            PreparedStatement statementCreateOrderProduct = connection.prepareStatement(SQL_CREATE_ORDER_PRODUCT);
            PreparedStatement statementCreateSelectOrder = connection.prepareStatement(SQL_SELECT_ID_BY_ALL);
            connection.setAutoCommit(false);
            statementCreateOrder.setLong(1,order.getId_user());
            statementCreateOrder.setBigDecimal(2,order.getPrice());
            statementCreateOrder.executeUpdate();
            statementCreateSelectOrder.setLong(1, order.getId_user());
            statementCreateSelectOrder.setBigDecimal(2,order.getPrice());
            ResultSet resultSet = statementCreateSelectOrder.executeQuery();
            if(resultSet.next()){
                order.setId(resultSet.getLong(DataBaseColumn.ID_ORDER));
            }
            for(Map.Entry<Long,Integer> product: order.getProduct().entrySet()) {
                statementCreateOrderProduct.setLong(1, order.getId());
                statementCreateOrderProduct.setLong(2,product.getKey());
                statementCreateOrderProduct.setInt(3,product.getValue());;
            }
            connection.commit();
            return order;
        } catch (SQLException e) {
            try {
                assert connection != null;
                connection.rollback();
                logger.error(e);
                throw new DaoException(e);
            } catch (SQLException exception) {
                logger.error(e);
                throw new DaoException(e);
            }
        } finally {
            close(connection);
        }
    }
    private Connection connection() throws SQLException {
        return connectionPool.getConnection();
    }
    private void close(Connection connection){
        try {
            if(connection!=null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
