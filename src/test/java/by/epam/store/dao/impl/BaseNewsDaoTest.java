package by.epam.store.dao.impl;

import by.epam.store.dao.FeedbackDao;
import by.epam.store.dao.NewsDao;
import by.epam.store.entity.*;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import by.epam.store.service.TypeSort;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.testng.Assert.*;

public class BaseNewsDaoTest {
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Connection connection;
    private CustomConnectionPool connectionPool;
    private NewsDao newsDao;
    private News news;

    @BeforeMethod
    public void setUp() throws SQLException {
        connection = Mockito.mock(Connection.class);
        connectionPool = Mockito.mock(CustomConnectionPool.class);
        statement = Mockito.mock(PreparedStatement.class);
        Mockito.when(connectionPool.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(Mockito.anyString())).thenReturn(statement);
        Mockito.when(connection.prepareStatement(Mockito.anyString(), Mockito.anyByte())).thenReturn(statement);
        newsDao = new BaseNewsDao(connectionPool);
        resultSet = Mockito.mock(ResultSet.class);
        news = News
                .builder()
                .idNews(1)
                .info("info")
                .date(new Date())
                .title("title")
                .imageName("image")
                .build();
        Mockito.when(resultSet.getLong(DataBaseColumn.ID_NEWS)).thenReturn(news.getIdNews());
        Mockito.when(resultSet.getString(DataBaseColumn.NEWS_TITLE)).thenReturn(news.getTitle());
        Mockito.when(resultSet.getString(DataBaseColumn.NEWS_INFO)).thenReturn(news.getInfo());
        Mockito.when(resultSet.getLong(DataBaseColumn.DATE)).thenReturn(news.getDate().getTime());
        Mockito.when(resultSet.getString(DataBaseColumn.NEWS_IMAGE)).thenReturn(news.getImageName());
        Mockito.when(resultSet.getLong(1)).thenReturn(news.getIdNews());
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        Mockito.when(statement.getGeneratedKeys()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true, false);
    }

    @Test
    public void testFindEntityById() throws DaoException {
        assertEquals(newsDao.findEntityById(1L), Optional.of(news));
    }

    @Test
    public void testDelete() throws DaoException {
        assertTrue(newsDao.delete(1L));
    }

    @Test
    public void testUpdate() throws DaoException {
        assertTrue(newsDao.update(news));
    }

    @Test
    public void testCreate() throws DaoException {
        assertEquals(newsDao.create(news),news);
    }

    @Test
    public void testFindFreshNews() throws DaoException {
        assertEquals(newsDao.findFreshNews(3), List.of(news));
    }

    @Test
    public void testFindSortNews() throws DaoException {
        assertEquals(newsDao.findSortNews(TypeSort.DATE.name(),0,1), List.of(news));
    }

    @Test
    public void testChangeImage() throws DaoException {
        assertTrue(newsDao.changeImage(news.getIdNews(),news.getImageName()));
    }
}