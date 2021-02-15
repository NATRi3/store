package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.ProductCollection;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import by.epam.store.util.MessageKey;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Log4j2
public class CollectionDao implements BaseDao<ProductCollection>, by.epam.store.dao.CollectionDao {
    public static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    public static final String SQL_SELECT_ALL = "SELECT id_collection, name, info, date FROM collection";
    @Override
    public List<ProductCollection> findAll() throws DaoException {
        try(Connection collection = connectionPool.getConnection();
            PreparedStatement statement = collection.prepareStatement(SQL_SELECT_ALL)){
            ResultSet resultSet = statement.executeQuery();
            List<ProductCollection> list = new ArrayList<>();
            while (resultSet.next()){
                list.add(getProductCollectionFromResultSet(resultSet));
            }
            return list;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(MessageKey.ERROR_MESSAGE_SERVER_PROBLEM);
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
        return null;
    }

    private ProductCollection getProductCollectionFromResultSet(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong(DataBaseColumn.ID_COLLECTION);
        String name = resultSet.getString(DataBaseColumn.COLLECTION_NAME);
        String info = resultSet.getString(DataBaseColumn.COLLECTION_INFO);
        Date date = new Date(resultSet.getLong(DataBaseColumn.DATE));
        return new ProductCollection(id,name,info,date);
    }
}
