package by.epam.store.dao.impl;

import by.epam.store.dao.CollectionDao;
import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import org.mockito.Mockito;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.*;

/**
 * The type Base collection dao test.
 */
public class BaseCollectionDaoTest {
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Connection connection;
    private CustomConnectionPool connectionPool;
    private CollectionDao collectionDao;
    private ProductCollection collection;


    /**
     * Sets up.
     *
     * @throws SQLException the sql exception
     */
    @BeforeMethod
    public void setUp() throws SQLException {
        connection = Mockito.mock(Connection.class);
        connectionPool = Mockito.mock(CustomConnectionPool.class);
        statement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(connection.prepareStatement(Mockito.anyString(),Mockito.anyByte())).thenReturn(statement);
        collectionDao = new BaseCollectionDao(connectionPool);
        resultSet = Mockito.mock(ResultSet.class);
        collection = ProductCollection
                .builder()
                .idCollection(1)
                .name("collection")
                .info("collection")
                .date(new Date())
                .build();
        Mockito.when(resultSet.getLong(DataBaseColumn.ID_COLLECTION)).thenReturn(collection.getIdCollection());
        Mockito.when(resultSet.getString(DataBaseColumn.COLLECTION_NAME)).thenReturn(collection.getName());
        Mockito.when(resultSet.getString(DataBaseColumn.COLLECTION_INFO)).thenReturn(collection.getInfo());
        Mockito.when(resultSet.getLong(DataBaseColumn.DATE)).thenReturn(collection.getDate().getTime());
        Mockito.when(resultSet.getLong(1)).thenReturn(collection.getIdCollection());
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        Mockito.when(statement.getGeneratedKeys()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true,false);
    }

    /**
     * Tear down.
     */
    @AfterMethod
    public void tearDown() {
    }

    /**
     * Test find all.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testFindAll() throws DaoException {
        assertEquals(collectionDao.findAll(), List.of(collection));
    }

    /**
     * Test create.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testCreate() throws DaoException {
        assertEquals(collectionDao.create(collection),collection);
    }

    /**
     * Test find by status.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testFindByStatus() throws DaoException {
        assertEquals(collectionDao.findByStatus(TypeStatus.ACTIVE),List.of(collection));
    }

    /**
     * Test update status.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testUpdateStatus() throws DaoException {
        assertTrue(collectionDao.updateStatus(1,TypeStatus.ACTIVE));
    }

    /**
     * Test update info.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testUpdateInfo() throws DaoException {
        assertTrue(collectionDao.updateInfo(collection.getIdCollection(),collection.getInfo()));
    }
}