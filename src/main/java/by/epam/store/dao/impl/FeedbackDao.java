package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.Feedback;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
public class FeedbackDao implements BaseDao<Feedback> {
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_SELECT_ALL = "SELECT id_feedback, feedback, evaluation, id_product, id_account FROM feedback";
    private static final String SQL_SELECT_BY_ID = "SELECT id_feedback, feedback, evaluation, id_product, id_account FROM feedback WHERE id_feedback=?";
    private static final String SQL_CREATE = "INSERT INTO feedback SET feedback=? AND evaluation=? AND id_product=? AND id_account=?";
    private static final String SQL_SELECT_BY_FEEDBACK_USER_PRODUCT = "SELECT id_feedback, feedback, evaluation, id_product, id_account FROM feedback WHERE feedback=? AND id_account=? AND id_product=?";

    @Override
    public List<Feedback> findAll() throws DaoException {
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong(DataBaseColumn.ID_FEEDBACK);
                String feedback = resultSet.getString(DataBaseColumn.FEEDBACK);
                byte evaluation = resultSet.getByte(DataBaseColumn.FEEDBACK_EVALUATION);
                long idProduct= resultSet.getLong(DataBaseColumn.FEEDBACK_ID_PRODUCT);
                long idAccount = resultSet.getLong(DataBaseColumn.FEEDBACK_ID_ACCOUNT);
                feedbacks.add(new Feedback(id,feedback,evaluation,idProduct,idAccount));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return feedbacks;
    }

    @Override
    public Optional<Feedback> findEntityById(Long aLong) throws DaoException {
        Optional<Feedback> optionalFeedback = Optional.empty();
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                long id = resultSet.getLong(DataBaseColumn.ID_FEEDBACK);
                String feedback = resultSet.getString(DataBaseColumn.FEEDBACK);
                byte evaluation = resultSet.getByte(DataBaseColumn.FEEDBACK_EVALUATION);
                long idProduct= resultSet.getLong(DataBaseColumn.FEEDBACK_ID_PRODUCT);
                long idAccount = resultSet.getLong(DataBaseColumn.FEEDBACK_ID_ACCOUNT);
                optionalFeedback = Optional.of(new Feedback(id,feedback,evaluation,idProduct,idAccount));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return optionalFeedback;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public Feedback update(Feedback feedback) throws DaoException {
        return null;
    }

    @Override
    public Feedback create(Feedback feedback) throws DaoException {
        try (Connection connection = connection();
             PreparedStatement statementUpdate = connection.prepareStatement(SQL_CREATE);
             PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT_BY_FEEDBACK_USER_PRODUCT)){
            statementUpdate.setString(1,feedback.getFeedback());
            statementUpdate.setInt(2,feedback.getEvaluation());
            statementUpdate.setLong(3,feedback.getIdProduct());
            statementUpdate.setLong(4,feedback.getIdUser());
            statementUpdate.executeUpdate();
            statementSelect.setString(1,feedback.getFeedback());
            statementSelect.setLong(2,feedback.getIdUser());
            statementSelect.setLong(3,feedback.getIdProduct());
            ResultSet resultSet = statementSelect.executeQuery();
            if(resultSet.next()){
                feedback.setId(resultSet.getLong(DataBaseColumn.ID_FEEDBACK));
            }
            return feedback;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    private Connection connection() throws SQLException {
        return connectionPool.getConnection();
    }
}
