package by.epam.store.model.dao.impl;

import by.epam.store.model.entity.ProductCollection;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.model.pool.CustomConnectionPool;
import by.epam.store.annotation.Bean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.epam.store.model.dao.impl.StatementUtil.setStatementParameters;

/**
 * The type Base collection dao.
 */
@Bean
public class BaseCollectionDao implements by.epam.store.model.dao.CollectionDao {
    private final static Logger log = LogManager.getLogger(BaseCollectionDao.class);
    private final CustomConnectionPool connectionPool;
    private static final String SQL_SELECT_ALL = "SELECT id_collection, name, info, date FROM l4tsmab3ywpoc8m0.collection";
    private static final String SQL_SELECT_BY_STATUS = "SELECT id_collection, name, info, date, status FROM l4tsmab3ywpoc8m0.collection WHERE status=?";
    private static final String SQL_INSERT = "INSERT INTO l4tsmab3ywpoc8m0.collection (`name`,`info`,`date`,`status`) VALUES (?,?,?,?);";
    private static final String SQL_SET_STATUS_BY_ID = "UPDATE l4tsmab3ywpoc8m0.collection SET status=? WHERE id_collection=?";
    private static final String SQL_SET_INFO_BY_ID = "UPDATE l4tsmab3ywpoc8m0.collection SET info=? WHERE id_collection=?";

    public BaseCollectionDao() {
        connectionPool = CustomConnectionPool.getInstance();
    }

    public BaseCollectionDao(CustomConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<ProductCollection> findAll() throws DaoException {
        try (Connection collection = connectionPool.getConnection();
             PreparedStatement statement = collection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            List<ProductCollection> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(getProductCollectionFromResultSet(resultSet));
            }
            return list;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<ProductCollection> findEntityById(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(ProductCollection productCollection) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public ProductCollection create(ProductCollection productCollection) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            setStatementParameters(statement,
                    productCollection.getName(),
                    productCollection.getInfo(),
                    productCollection.getDate().getTime(),
                    TypeStatus.ACTIVE.toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                productCollection.setIdCollection(resultSet.getLong(1));
            }
            return productCollection;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException();
        }
    }

    @Override
    public List<ProductCollection> findByStatus(TypeStatus typeStatus) throws DaoException {
        try (Connection collection = connectionPool.getConnection();
             PreparedStatement statement = collection.prepareStatement(SQL_SELECT_BY_STATUS)) {
            statement.setString(1, typeStatus.toString());
            ResultSet resultSet = statement.executeQuery();
            List<ProductCollection> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(getProductCollectionFromResultSet(resultSet));
            }
            return list;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updateStatus(long idCollection, TypeStatus status) throws DaoException {
        try (Connection collection = connectionPool.getConnection();
             PreparedStatement statement = collection.prepareStatement(SQL_SET_STATUS_BY_ID)) {
            setStatementParameters(statement,status.toString(), idCollection);
            return 1 == statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean updateInfo(Long id, String info) throws DaoException {
        try (Connection collection = connectionPool.getConnection();
             PreparedStatement statement = collection.prepareStatement(SQL_SET_INFO_BY_ID)) {
            setStatementParameters(statement, info, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    private ProductCollection getProductCollectionFromResultSet(ResultSet resultSet) throws SQLException {
        return ProductCollection
                .builder()
                .idCollection(resultSet.getLong(DataBaseColumn.ID_COLLECTION))
                .name(resultSet.getString(DataBaseColumn.COLLECTION_NAME))
                .info(resultSet.getString(DataBaseColumn.COLLECTION_INFO))
                .date(new Date(resultSet.getLong(DataBaseColumn.DATE)))
                .build();
    }
}
