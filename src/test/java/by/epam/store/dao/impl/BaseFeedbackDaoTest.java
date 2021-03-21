package by.epam.store.dao.impl;

import by.epam.store.dao.FeedbackDao;
import by.epam.store.entity.Feedback;
import by.epam.store.entity.TypeRole;
import by.epam.store.entity.TypeStatus;
import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
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

/**
 * The type Base feedback dao test.
 */
public class BaseFeedbackDaoTest {
    private ResultSet resultSet;
    private PreparedStatement statement;
    private Connection connection;
    private CustomConnectionPool connectionPool;
    private FeedbackDao feedbackDao;
    private Feedback feedback;

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
        Mockito.when(connection.prepareStatement(Mockito.anyString(), Mockito.anyByte())).thenReturn(statement);
        feedbackDao = new BaseFeedbackDao(connectionPool);
        resultSet = Mockito.mock(ResultSet.class);
        feedback = Feedback
                .builder()
                .id(1)
                .date(new Date())
                .user(User
                        .builder()
                        .id(1)
                        .name("user")
                        .email("test@mail.com")
                        .access(TypeStatus.ACTIVE)
                        .imageName("test")
                        .registerDate(new Date())
                        .role(TypeRole.CLIENT)
                        .build())
                .feedback("feedback")
                .evaluation((byte) 1)
                .idProduct(1)
                .build();
        Mockito.when(resultSet.getLong(DataBaseColumn.ID_FEEDBACK)).thenReturn(feedback.getId());
        Mockito.when(resultSet.getString(DataBaseColumn.FEEDBACK)).thenReturn(feedback.getFeedback());
        Mockito.when(resultSet.getByte(DataBaseColumn.FEEDBACK_EVALUATION)).thenReturn(feedback.getEvaluation());
        Mockito.when(resultSet.getLong(DataBaseColumn.FEEDBACK_ID_PRODUCT)).thenReturn(feedback.getIdProduct());
        Mockito.when(resultSet.getLong(DataBaseColumn.ID_ACCOUNT)).thenReturn(feedback.getUser().getId());
        Mockito.when(resultSet.getString(DataBaseColumn.ACCOUNT_NAME)).thenReturn(feedback.getUser().getName());
        Mockito.when(resultSet.getString(DataBaseColumn.ACCOUNT_EMAIL)).thenReturn(feedback.getUser().getEmail());
        Mockito.when(resultSet.getString(DataBaseColumn.ACCOUNT_IMAGE)).thenReturn(feedback.getUser().getImageName());
        Mockito.when(resultSet.getLong(DataBaseColumn.ACCOUNT_REGISTER_DATE)).thenReturn(feedback.getUser().getRegisterDate().getTime());
        Mockito.when(resultSet.getString(DataBaseColumn.ACCOUNT_ACCESS)).thenReturn(feedback.getUser().getAccess().name());
        Mockito.when(resultSet.getString(DataBaseColumn.ACCOUNT_ROLE)).thenReturn(feedback.getUser().getRole().name());
        Mockito.when(resultSet.getLong(DataBaseColumn.DATE)).thenReturn(feedback.getDate().getTime());
        Mockito.when(resultSet.getLong(1)).thenReturn(feedback.getId());
        Mockito.when(statement.executeQuery()).thenReturn(resultSet);
        Mockito.when(statement.executeUpdate()).thenReturn(1);
        Mockito.when(statement.getGeneratedKeys()).thenReturn(resultSet);
        Mockito.when(resultSet.next()).thenReturn(true, false);
    }

    /**
     * Test find all.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testFindAll() throws DaoException {
        assertEquals(feedbackDao.findAll(), List.of(feedback));
    }

    /**
     * Test find all by product id.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testFindAllByProductId() throws DaoException {
        assertEquals(feedbackDao.findAllByProductId(1L),List.of(feedback));
    }

    /**
     * Test find entity by id.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testFindEntityById() throws DaoException {
        assertEquals(feedbackDao.findEntityById(1L), Optional.of(feedback));
    }

    /**
     * Test delete.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testDelete() throws DaoException {
        assertTrue(feedbackDao.delete(1L));
    }

    /**
     * Test create.
     *
     * @throws DaoException the dao exception
     */
    @Test
    public void testCreate() throws DaoException {
        assertEquals(feedbackDao.create(feedback), feedback);
    }
}