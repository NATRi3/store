package by.epam.store.model.dao.impl;

import by.epam.store.model.entity.*;
import by.epam.store.exception.DaoException;
import by.epam.store.model.pool.CustomConnectionPool;
import by.epam.store.model.service.TypeSort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * The type Base order dao.
 */
public class BaseOrderDao implements by.epam.store.model.dao.OrderDao {
    private final static Logger log = LogManager.getLogger(BaseOrderDao.class);
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_INSERT_ORDER = "INSERT INTO l4tsmab3ywpoc8m0.orders SET id_account = ?, price = ?, phone=?, address=?, date=?";
    private static final String SQL_INSERT_ORDER_PRODUCT = "INSERT INTO l4tsmab3ywpoc8m0.order_product SET id_order = ?, id_product = ?, product_amount=?";
    private static final String SQL_SELECT_ORDER_BY_USER = "SELECT id_orders, id_account, price, phone, address, status, date FROM l4tsmab3ywpoc8m0.orders WHERE id_account=?";
    private static final String SQL_SELECT_PRODUCTS_BY_ID_ORDER =
            "SELECT id_order_product, id_order, id_product, product_amount, id_products, name, info, price, status, image, id_collection, evaluation FROM l4tsmab3ywpoc8m0.order_product join(SELECT id_products,name,info,price,status,image,id_collection,t1.evaluation" +
                    " from l4tsmab3ywpoc8m0.products LEFT JOIN (SELECT id_product,AVG(evaluation) as 'evaluation' FROM l4tsmab3ywpoc8m0.feedback) as t1 on t1.id_product=id_products ) p" +
                    " on order_product.id_product=p.id_products WHERE id_order=?";
    private static final String SQL_SELECT_ORDERS_BY_STATUS = "SELECT id_orders, price, phone, address, status, date, id_accounts, email, role, password, name, image, access, register_date FROM l4tsmab3ywpoc8m0.orders " +
            "JOIN accounts a on a.id_accounts = orders.id_account ORDER BY %s LIMIT 10 OFFSET ?";

    @Override
    public List<Order> findAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Order> findEntityById(Long aLong) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Order order) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Order create(Order order) throws DaoException {
        Connection connection = null;
        PreparedStatement statementCreateOrder = null;
        PreparedStatement statementCreateOrderProduct = null;
        try {
            try {
                connection = connectionPool.getConnection();
                connection.setAutoCommit(false);
                statementCreateOrder = connection.prepareStatement(SQL_INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
                statementCreateOrderProduct = connection.prepareStatement(SQL_INSERT_ORDER_PRODUCT);
                statementCreateOrder.setLong(1, order.getIdUser());
                statementCreateOrder.setBigDecimal(2, order.getPrice());
                statementCreateOrder.setString(3, order.getPhone());
                statementCreateOrder.setString(4, order.getAddress());
                statementCreateOrder.setLong(5, order.getDate().getTime());
                statementCreateOrder.executeUpdate();
                ResultSet resultSet = statementCreateOrder.getGeneratedKeys();
                if (resultSet.next()) {
                    order.setId(resultSet.getLong(1));
                }
                for (Map.Entry<Product, Integer> product : order.getProduct().entrySet()) {
                    statementCreateOrderProduct.setLong(1, order.getId());
                    statementCreateOrderProduct.setLong(2, product.getKey().getId());
                    statementCreateOrderProduct.setInt(3, product.getValue());
                    statementCreateOrderProduct.executeUpdate();
                }
                connection.commit();
            } catch (SQLException e) {
                if (connection != null) {
                    connection.rollback();
                }
                log.error(e);
                throw new DaoException(e);
            } finally {
                assert connection != null;
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        } finally {
            close(statementCreateOrder);
            close(statementCreateOrderProduct);
            close(connection);
        }
        return order;
    }


    @Override
    public List<Order> findUserOrders(long id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statementOrder = connection.prepareStatement(SQL_SELECT_ORDER_BY_USER);
             PreparedStatement statementProduct = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_ID_ORDER)) {
            statementOrder.setLong(1, id);
            ResultSet resultSet = statementOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                orderList.add(getOrderFromResultSet(resultSet));
            }
            for (Order order : orderList) {
                statementProduct.setLong(1, order.getId());
                ResultSet resultSetProduct = statementProduct.executeQuery();
                setMapFromResultSet(resultSetProduct, order);
            }
            return orderList;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Order> findOrdersByStatusAndSort(int beginPagination, TypeSort typeSort) throws DaoException {
        String sql = String.format(SQL_SELECT_ORDERS_BY_STATUS, typeSort.toString());
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statementOrder = connection.prepareStatement(sql);
             PreparedStatement statementProduct = connection.prepareStatement(SQL_SELECT_PRODUCTS_BY_ID_ORDER)) {
            statementOrder.setLong(1, beginPagination);
            ResultSet resultSet = statementOrder.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = getOrderFromResultSet(resultSet);
                order.setUser(BaseUserDao.createUserFormResultSet(resultSet));
                orderList.add(order);
            }
            for (Order order : orderList) {
                statementProduct.setLong(1, order.getId());
                ResultSet resultSetProduct = statementProduct.executeQuery();
                setMapFromResultSet(resultSetProduct, order);
            }
            return orderList;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        return Order
                .builder()
                .id(resultSet.getLong(DataBaseColumn.ID_ORDER))
                .address(resultSet.getString(DataBaseColumn.ORDER_ADDRESS))
                .phone(resultSet.getString(DataBaseColumn.ORDER_PHONE))
                .price(resultSet.getBigDecimal(DataBaseColumn.ORDER_PRICE))
                .date(new Date(resultSet.getLong(DataBaseColumn.DATE)))
                .build();
    }

    private void setMapFromResultSet(ResultSet resultSet, Order order) throws SQLException {
        Map<Product, Integer> productMap = new HashMap<>();
        while (resultSet.next()) {
            productMap.put(
                Product.builder()
                        .id(resultSet.getLong(DataBaseColumn.ID_PRODUCT))
                        .name(resultSet.getString(DataBaseColumn.PRODUCT_NAME))
                        .info(resultSet.getString(DataBaseColumn.PRODUCT_INFO))
                        .price(resultSet.getBigDecimal(DataBaseColumn.PRODUCT_PRICE))
                        .status(TypeStatus.valueOf(resultSet.getString(DataBaseColumn.STATUS)))
                        .idCollection(resultSet.getLong(DataBaseColumn.PRODUCT_ID_COLLECTION))
                        .imageName(resultSet.getString(DataBaseColumn.PRODUCT_IMAGE))
                        .rating(resultSet.getString(DataBaseColumn.FEEDBACK_EVALUATION))
                        .countInOrder(resultSet.getInt(DataBaseColumn.ORDER_PRODUCT_PRODUCT_AMOUNT))
                        .build(),
                resultSet.getInt(DataBaseColumn.ORDER_PRODUCT_PRODUCT_AMOUNT)
            );
        }
        order.setProduct(productMap);
    }
}
