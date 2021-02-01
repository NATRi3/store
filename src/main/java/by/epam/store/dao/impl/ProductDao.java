package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import by.epam.store.util.MessageErrorKey;
import lombok.extern.log4j.Log4j2;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Log4j2
public class ProductDao implements BaseDao<Product>, by.epam.store.dao.ProductDao {
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_SELECT_ALL =
        "SELECT id_products,name,info,price,status FROM products";
    private static final String SQL_INSERT_PRODUCT =
        "INSERT INTO products (`name`, `info`,`price`)VALUES (?, ?, ?)";
    public static final String SQL_SELECT_BY_ID =
            "SELECT id_products,name,info,price,status FROM products WHERE id_products=?";
    private static final String SQL_UPDATE_PRODUCT_BY_ID =
        "UPDATE products SET name = ? AND info = ? AND price = ? AND status = ? WHERE id_products = ?";
    private static final String SQL_UPDATE_STATUS_BY_ID = "UPDATE products SET status = ? WHERE id_products=?";


    @Override
    public List<Product> findAll() throws DaoException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getInt(DataBaseColumn.ID_PRODUCT);
                String name = resultSet.getString(DataBaseColumn.PRODUCT_NAME);
                String info = resultSet.getString(DataBaseColumn.PRODUCT_INFO);
                BigDecimal price = resultSet.getBigDecimal(DataBaseColumn.PRODUCT_PRICE);
                TypeStatus status = TypeStatus.valueOf(resultSet.getString(DataBaseColumn.PRODUCT_STATUS));
                products.add(new Product(id,name,info,price,status));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
        return products;
    }

    @Override
    public Optional<Product> findEntityById(Long id) throws DaoException {
        Optional<Product> optionalProduct = Optional.empty();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long idProduct = resultSet.getInt(DataBaseColumn.ID_PRODUCT);
                String name = resultSet.getString(DataBaseColumn.PRODUCT_NAME);
                String info = resultSet.getString(DataBaseColumn.PRODUCT_INFO);
                BigDecimal price = resultSet.getBigDecimal(DataBaseColumn.PRODUCT_PRICE);
                TypeStatus status = TypeStatus.valueOf(resultSet.getString(DataBaseColumn.PRODUCT_STATUS));
                Product product = new Product(idProduct,name,info,price,status);
                optionalProduct = Optional.of(product);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
        return optionalProduct;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean changeStatus(Long id, TypeStatus status) throws DaoException{
        boolean result = false;
        try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS_BY_ID)){
            statement.setString(1,status.toString());
            statement.setLong(2, id);
            if(statement.executeUpdate()==1){
                result = true;
            }
        } catch (SQLException e){
            log.error(e);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
        return result;
    }
    @Override
    public Product create(Product product) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCT)){
            statement.setString(1, product.getName());
            statement.setString(2, product.getInfo());
            statement.setBigDecimal(3, product.getPrice());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Product update(Product product) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT_BY_ID)){
            statement.setString(1, product.getName());
            statement.setString(2, product.getInfo());
            statement.setBigDecimal(3, product.getPrice());
            statement.setString(4, String.valueOf(product.getStatus()));
            statement.setLong(5, product.getId());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    private Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }
}
