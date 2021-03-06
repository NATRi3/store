package by.epam.store.model.dao.impl;

import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.model.pool.CustomConnectionPool;
import by.epam.store.annotation.Bean;
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

import static by.epam.store.model.dao.impl.StatementUtil.setStatementParameters;

/**
 * The type Base product dao.
 */
@Bean
public class BaseProductDao implements by.epam.store.model.dao.ProductDao {
    private final static Logger log = LogManager.getLogger(BaseProductDao.class);
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_SELECT_ALL =
            "SELECT id_products,name,info,price,status,image,id_collection,AVG(t1.evaluation) as 'evaluation' from " +
                    "l4tsmab3ywpoc8m0.products LEFT JOIN (SELECT id_product,evaluation FROM l4tsmab3ywpoc8m0.feedback) as t1 on id_products=t1.id_product";
    private static final String SQL_INSERT_PRODUCT =
            "INSERT INTO l4tsmab3ywpoc8m0.products (`name`, `info`,`price`,`id_collection`)VALUES (?, ?, ?,?)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id_products,name,info,price,status,image,id_collection,AVG(t1.evaluation) as 'evaluation' from " +
                    "l4tsmab3ywpoc8m0.products LEFT JOIN (SELECT id_product,evaluation FROM l4tsmab3ywpoc8m0.feedback) as t1 on id_products=t1.id_product WHERE id_products=?";
    private static final String SQL_UPDATE_PRODUCT_BY_ID =
            "UPDATE l4tsmab3ywpoc8m0.products SET name = ?, info = ?, price = ?, status = ?, image=? WHERE id_products = ?";
    private static final String SQL_UPDATE_STATUS_BY_ID =
            "UPDATE l4tsmab3ywpoc8m0.products SET status = ? WHERE id_products=?";
    private static final String SQL_SELECT_SORTED_COLLECTION_PRODUCT =
            "SELECT id_products,name,info,image, id_collection,status,price,evaluation FROM products LEFT JOIN" +
                    "(SELECT id_product,evaluation from (SELECT id_product,AVG(evaluation) as evaluation from l4tsmab3ywpoc8m0.feedback GROUP BY id_product)as t1)" +
                    "as f on f.id_product=id_products WHERE status=? and id_collection LIKE ? order by %s limit 12 offset ?";
    private static final String SQL_SELECT_RANDOM_PRODUCT =
            "SELECT id_products,name,info,image, id_collection,status,price,evaluation FROM l4tsmab3ywpoc8m0.products LEFT JOIN" +
                    "(SELECT id_product,evaluation from (SELECT id_product,AVG(evaluation) as evaluation from l4tsmab3ywpoc8m0.feedback GROUP BY id_product)as t1)" +
                    "as f on f.id_product=id_products WHERE status='ACTIVE' order by rand() limit ?";
    private static final String SQL_SELECT_BY_NAME = "SELECT id_products,name,info,image,id_collection,status,price" +
            " FROM l4tsmab3ywpoc8m0.products WHERE name LIKE ? %s limit 6 ";


    @Override
    public List<Product> findAll() throws DaoException {
        List<Product> products = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                products.add(getProductFormResultSet(resultSet));
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
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                optionalProduct = Optional.of(getProductFormResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return optionalProduct;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean changeStatus(Long id, TypeStatus status) throws DaoException {
        boolean result = false;
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_STATUS_BY_ID)) {
            setStatementParameters(statement, status.toString(), id);
            if (statement.executeUpdate() == 1) {
                result = true;
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Product> findCollectionProductAndSort(int begin, TypeStatus status, String idCollection, String typeSort)
            throws DaoException {
        String sql = String.format(SQL_SELECT_SORTED_COLLECTION_PRODUCT, typeSort);
        List<Product> result = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            setStatementParameters(statement, status.toString(), idCollection, begin);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(getProductFormResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Product> findRandomProduct(int amount) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_RANDOM_PRODUCT)) {
            statement.setInt(1, amount);
            ResultSet resultSet = statement.executeQuery();
            List<Product> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(getProductFormResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public List<Product> findProductByName(String... names) throws DaoException {
        StringBuilder append = new StringBuilder();
        for (int i = 1; i < names.length; i++) {
            append.append("AND name LIKE ?");
        }
        String sql = String.format(SQL_SELECT_BY_NAME, append);
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            for (int i = 1; i <= names.length; i++) {
                statement.setString(i, names[i - 1]);
            }
            ResultSet resultSet = statement.executeQuery();
            List<Product> resultList = new ArrayList<>();
            while (resultSet.next()) {
                long id = resultSet.getLong(DataBaseColumn.ID_PRODUCT);
                String productName = resultSet.getString(DataBaseColumn.PRODUCT_NAME);
                String info = resultSet.getString(DataBaseColumn.PRODUCT_INFO);
                TypeStatus status = TypeStatus.valueOf(resultSet.getString(DataBaseColumn.STATUS));
                BigDecimal price = resultSet.getBigDecimal(DataBaseColumn.PRODUCT_PRICE);
                String image = resultSet.getString(DataBaseColumn.PRODUCT_IMAGE);
                long idCollection = resultSet.getLong(DataBaseColumn.PRODUCT_ID_COLLECTION);
                resultList.add(Product.builder()
                        .id(id)
                        .name(productName)
                        .info(info)
                        .status(status)
                        .price(price)
                        .imageName(image)
                        .idCollection(idCollection)
                        .build());
            }
            return resultList;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Product create(Product product) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_PRODUCT)) {
            setStatementParameters(statement,
                    product.getName(),
                    product.getInfo(),
                    product.getPrice(),
                    product.getIdCollection());
            statement.executeUpdate();
            return product;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Product product) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PRODUCT_BY_ID)) {
            setStatementParameters(statement,
                    product.getName(),
                    product.getInfo(),
                    product.getPrice(),
                    String.valueOf(product.getStatus()),
                    product.getImageName(),
                    product.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    private Product getProductFormResultSet(ResultSet resultSet) throws SQLException {
        String evaluation = resultSet.getString(DataBaseColumn.FEEDBACK_EVALUATION);
        return Product
                .builder()
                .id(resultSet.getLong(DataBaseColumn.ID_PRODUCT))
                .name(resultSet.getString(DataBaseColumn.PRODUCT_NAME))
                .info(resultSet.getString(DataBaseColumn.PRODUCT_INFO))
                .status(TypeStatus.valueOf(resultSet.getString(DataBaseColumn.STATUS)))
                .price(resultSet.getBigDecimal(DataBaseColumn.PRODUCT_PRICE))
                .imageName(resultSet.getString(DataBaseColumn.PRODUCT_IMAGE))
                .idCollection(resultSet.getLong(DataBaseColumn.PRODUCT_ID_COLLECTION))
                .rating(evaluation != null?evaluation.substring(0,3):"Нет оценок")
                .build();
    }
}
