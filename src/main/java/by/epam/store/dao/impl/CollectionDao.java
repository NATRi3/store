package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class CollectionDao implements by.epam.store.dao.CollectionDao {
    public static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    public static final String SQL_SELECT_ALL = "SELECT id_collection, name, info, date FROM l4tsmab3ywpoc8m0.collection";
    public static final String SQL_SELECT_BY_STATUS = "SELECT id_collection, name, info, date, status FROM l4tsmab3ywpoc8m0.collection WHERE status=?";
    private final static Logger log = LogManager.getLogger(CollectionDao.class);
    private static final String SQL_INSERT = "INSERT INTO l4tsmab3ywpoc8m0.collection (`name`,`info`,`date`,`status`) VALUES (?,?,?,?);";
    private static final String SQL_SET_STATUS_BY_ID = "UPDATE l4tsmab3ywpoc8m0.collection SET status=? WHERE id_collection=?";
    private static final String SQL_SET_INFO_BY_ID = "UPDATE l4tsmab3ywpoc8m0.collection SET info=? WHERE id_collection=?";

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
        return Optional.empty();
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(ProductCollection productCollection) throws DaoException {
        return false;
    }

    @Override
    public ProductCollection create(ProductCollection productCollection) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, productCollection.getName());
            statement.setString(2, productCollection.getInfo());
            statement.setLong(3, productCollection.getDate().getTime());
            statement.setString(4, TypeStatus.ACTIVE.toString());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                productCollection.setIdCollection(resultSet.getLong(1));
            }
            return productCollection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ProductCollection getProductCollectionFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DataBaseColumn.ID_COLLECTION);
        String name = resultSet.getString(DataBaseColumn.COLLECTION_NAME);
        String info = resultSet.getString(DataBaseColumn.COLLECTION_INFO);
        Date date = new Date(resultSet.getLong(DataBaseColumn.DATE));
        return new ProductCollection(id, name, info, date);
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
            statement.setString(1, status.toString());
            statement.setLong(2, idCollection);
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
            statement.setString(1, info);
            statement.setLong(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }
}
