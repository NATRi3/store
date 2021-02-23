package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDao implements BaseDao<Product>, by.epam.store.dao.ProductDao {
    private final static Logger log = LogManager.getLogger(ProductDao.class);
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_SELECT_ALL =
            "SELECT id_products,name,info,price,status,image,id_collection,AVG(t1.evaluation) as 'evaluation' from " +
                    "l4tsmab3ywpoc8m0.products LEFT JOIN (SELECT id_product,evaluation FROM l4tsmab3ywpoc8m0.feedback) as t1 on id_products=t1.id_product";
    private static final String SQL_INSERT_PRODUCT =
            "INSERT INTO l4tsmab3ywpoc8m0.products (`name`, `info`,`price`,`id_collection`)VALUES (?, ?, ?,?)";
    public static final String SQL_SELECT_BY_ID =
            "SELECT id_products,name,info,price,status,image,id_collection,AVG(t1.evaluation) as 'evaluation' from " +
                    "l4tsmab3ywpoc8m0.products LEFT JOIN (SELECT id_product,evaluation FROM l4tsmab3ywpoc8m0.feedback) as t1 on id_products=t1.id_product WHERE id_products=?";
    private static final String SQL_UPDATE_PRODUCT_BY_ID =
            "UPDATE l4tsmab3ywpoc8m0.products SET name = ?, info = ?, price = ?, status = ?, image=? WHERE id_products = ?";
    private static final String SQL_UPDATE_STATUS_BY_ID =
            "UPDATE l4tsmab3ywpoc8m0.products SET status = ? WHERE id_products=?";
    public static final String SQL_SELECT_SORTED_COLLECTION_PRODUCT =
            "SELECT id_products,name,info,image, id_collection,status,price,evaluation FROM products LEFT JOIN"+
            "(SELECT id_product,evaluation from (SELECT id_product,AVG(evaluation) as evaluation from l4tsmab3ywpoc8m0.feedback GROUP BY id_product)as t1)"+
            "as f on f.id_product=id_products WHERE status=? and id_collection LIKE ? order by %s limit 12 offset ?";
    public static final String SQL_SELECT_SORTED_COLLECTION_PRODUCT_COUNT =
            "SELECT count() FROM l4tsmab3ywpoc8m0.products WHERE status=? and id_collection LIKE ?";
    public static final String SQL_SELECT_RANDOM_PRODUCT =
            "SELECT id_products,name,info,image, id_collection,status,price,evaluation FROM l4tsmab3ywpoc8m0.products LEFT JOIN"+
            "(SELECT id_product,evaluation from (SELECT id_product,AVG(evaluation) as evaluation from l4tsmab3ywpoc8m0.feedback GROUP BY id_product)as t1)"+
            "as f on f.id_product=id_products WHERE status='ACTIVE' order by rand() limit ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id_products,name,info,image,id_collection,status,price" +
            " FROM l4tsmab3ywpoc8m0.products WHERE name LIKE ? limit 6 ";


    @Override
    public List<Product> findAll() throws DaoException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Optional<Product> optionalProduct = getProductFormResultSet(resultSet);
                optionalProduct.ifPresent(products::add);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
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
            if (resultSet.next()){
                optionalProduct = getProductFormResultSet(resultSet);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
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
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Product> findCollectionProductAndSort(int begin, TypeStatus status, String idCollection, String typeSort)
            throws DaoException {
        String sql = String.format(SQL_SELECT_SORTED_COLLECTION_PRODUCT,typeSort);
        List<Product> result = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1,status.toString());
            statement.setString(2,idCollection);
            statement.setInt(3,begin);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Optional<Product> optionalProduct = getProductFormResultSet(resultSet);
                optionalProduct.ifPresent(result::add);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
     public List<Product> findRandomProduct(int amount) throws DaoException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_RANDOM_PRODUCT)){
            statement.setInt(1,amount);
            ResultSet resultSet = statement.executeQuery();
            List<Product> result = new ArrayList<>();
            while (resultSet.next()){
                Optional<Product> optionalProduct = getProductFormResultSet(resultSet);
                optionalProduct.ifPresent(result::add);
            }
            return result;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Product> findProductByName(String name) throws DaoException {
        try(Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_NAME)){
            statement.setString(1,name);
            ResultSet resultSet = statement.executeQuery();
            List<Product> resultList = new ArrayList<>();
            while(resultSet.next()){
                long id = resultSet.getLong(DataBaseColumn.ID_PRODUCT);
                String productName = resultSet.getString(DataBaseColumn.PRODUCT_NAME);
                String info = resultSet.getString(DataBaseColumn.PRODUCT_INFO);
                TypeStatus status = TypeStatus.valueOf(resultSet.getString(DataBaseColumn.STATUS));
                BigDecimal price = resultSet.getBigDecimal(DataBaseColumn.PRODUCT_PRICE);
                String image = resultSet.getString(DataBaseColumn.PRODUCT_IMAGE);
                long idCollection = resultSet.getLong(DataBaseColumn.PRODUCT_ID_COLLECTION);
                resultList.add(new Product(id,productName,info,status,price,image,idCollection));
            }
            return resultList;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Product create(Product product) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCT)){
            statement.setString(1, product.getName());
            statement.setString(2, product.getInfo());
            statement.setBigDecimal(3, product.getPrice());
            statement.setLong(4,product.getIdCollection());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Product product) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT_BY_ID)){
            statement.setString(1, product.getName());
            statement.setString(2, product.getInfo());
            statement.setBigDecimal(3, product.getPrice());
            statement.setString(4, String.valueOf(product.getStatus()));
            statement.setString(5, product.getImageName());
            statement.setLong(6, product.getId());
            return statement.executeUpdate()==1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    private Optional<Product> getProductFormResultSet(ResultSet resultSet) throws SQLException {
        if(resultSet.getString(DataBaseColumn.ID_PRODUCT)!=null) {
            long id = resultSet.getLong(DataBaseColumn.ID_PRODUCT);
            String name = resultSet.getString(DataBaseColumn.PRODUCT_NAME);
            String info = resultSet.getString(DataBaseColumn.PRODUCT_INFO);
            TypeStatus status = TypeStatus.valueOf(resultSet.getString(DataBaseColumn.STATUS));
            BigDecimal price = resultSet.getBigDecimal(DataBaseColumn.PRODUCT_PRICE);
            String image = resultSet.getString(DataBaseColumn.PRODUCT_IMAGE);
            long idCollection = resultSet.getLong(DataBaseColumn.PRODUCT_ID_COLLECTION);
            String rating;
            if(resultSet.getString(DataBaseColumn.FEEDBACK_EVALUATION)!=null) {
                rating = String.valueOf(resultSet.getByte(DataBaseColumn.FEEDBACK_EVALUATION))+"/5";
            } else {
                rating = "Нет оценок";
            }
            return Optional.of(new Product(id, name, info, status, price, image, idCollection, rating));
        } else {
            return Optional.empty();
        }
    }

    private Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }
}
