package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.Feedback;
import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import by.epam.store.util.MessageKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FeedbackDao implements by.epam.store.dao.FeedbackDao, BaseDao<Feedback> {
    private final static Logger log = LogManager.getLogger(FeedbackDao.class);
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_SELECT_ALL =
            "SELECT id_feedback, feedback, evaluation, id_product, date, id_accounts, email, name, role, image, access, register_date FROM feedback JOIN accounts a on a.id_accounts = feedback.id_account";
    private static final String SQL_SELECT_ALL_BY_PRODUCT_ID =
            "SELECT id_feedback, feedback, evaluation, id_product, date, id_accounts, email, name, role, image, access, register_date FROM feedback JOIN accounts a on a.id_accounts = feedback.id_account WHERE id_product=?";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id_feedback, feedback, evaluation, id_product, id_accounts, email, name, role, image, access, register_date FROM feedback JOIN accounts a on a.id_accounts = feedback.id_account WHERE id_feedback=?";
    private static final String SQL_CREATE =
            "INSERT INTO feedback SET feedback=?, evaluation=?, id_product=?, id_account=?, date=?";

    @Override
    public List<Feedback> findAll() throws DaoException {
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Optional<Feedback> optionalFeedback = getFeedbackFromResultSet(resultSet);
                optionalFeedback.ifPresent(feedbacks::add);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(MessageKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
        return feedbacks;
    }

    @Override
    public List<Feedback> findAllByProductId(long id) throws DaoException {
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BY_PRODUCT_ID)){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                Optional<Feedback> optionalFeedback = getFeedbackFromResultSet(resultSet);
                optionalFeedback.ifPresent(feedbacks::add);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(MessageKey.ERROR_MESSAGE_SERVER_PROBLEM);
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
                optionalFeedback = getFeedbackFromResultSet(resultSet);
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(MessageKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
        return optionalFeedback;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean update(Feedback feedback) throws DaoException {
        return false;
    }

    @Override
    public Feedback create(Feedback feedback) throws DaoException {
        try (Connection connection = connection();
             PreparedStatement statementUpdate = connection.prepareStatement(SQL_CREATE,Statement.RETURN_GENERATED_KEYS)){
            statementUpdate.setString(1,feedback.getFeedback());
            statementUpdate.setInt(2,feedback.getEvaluation());
            statementUpdate.setLong(3,feedback.getIdProduct());
            statementUpdate.setLong(4,feedback.getUser().getId());
            statementUpdate.setLong(5,feedback.getDate().getTime());
            statementUpdate.executeUpdate();
            return feedback;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(MessageKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
    }

    private Optional<Feedback> getFeedbackFromResultSet(ResultSet resultSet) throws SQLException {
        if(resultSet.getString(DataBaseColumn.ID_FEEDBACK)!=null) {
            long id = resultSet.getLong(DataBaseColumn.ID_FEEDBACK);
            String feedback = resultSet.getString(DataBaseColumn.FEEDBACK);
            byte evaluation = resultSet.getByte(DataBaseColumn.FEEDBACK_EVALUATION);
            long idProduct = resultSet.getLong(DataBaseColumn.FEEDBACK_ID_PRODUCT);
            long idUser = resultSet.getInt(DataBaseColumn.ID_ACCOUNT);
            String name = resultSet.getString(DataBaseColumn.ACCOUNT_NAME);
            String email = resultSet.getString(DataBaseColumn.ACCOUNT_EMAIL);
            String image = resultSet.getString(DataBaseColumn.ACCOUNT_IMAGE);
            Date date = new Date(resultSet.getLong(DataBaseColumn.DATE));
            TypeStatus access = TypeStatus.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ACCESS));
            TypeRole role = TypeRole.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ROLE));
            java.util.Date dateRegister = new Date(resultSet.getLong(DataBaseColumn.ACCOUNT_REGISTER_DATE));
            User user = new User(idUser,email,role,name,image,access,dateRegister);
            return Optional.of(new Feedback(id, feedback, evaluation, idProduct, user, date));
        } else {
            return Optional.empty();
        }
    }

    private Connection connection() throws SQLException {
        return connectionPool.getConnection();
    }
}
