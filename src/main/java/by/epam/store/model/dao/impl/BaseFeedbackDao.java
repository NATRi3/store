package by.epam.store.model.dao.impl;

import by.epam.store.annotation.Bean;
import by.epam.store.exception.DaoException;
import by.epam.store.model.entity.Feedback;
import by.epam.store.model.entity.TypeRole;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.model.entity.User;
import by.epam.store.model.pool.CustomConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static by.epam.store.model.dao.impl.StatementUtil.setStatementParameters;

/**
 * The type Base feedback dao.
 */
@Bean
public class BaseFeedbackDao implements by.epam.store.model.dao.FeedbackDao {
    private final static Logger log = LogManager.getLogger(BaseFeedbackDao.class);
    private static final String SQL_SELECT_ALL =
            "SELECT id_feedback, feedback, evaluation, id_product, date, id_accounts, email, name, role, image, access, register_date FROM l4tsmab3ywpoc8m0.feedback JOIN l4tsmab3ywpoc8m0.accounts a on a.id_accounts = feedback.id_account";
    private static final String SQL_SELECT_ALL_BY_PRODUCT_ID =
            "SELECT id_feedback, feedback, evaluation, id_product, date, id_accounts, email, name, role, image, access, register_date FROM l4tsmab3ywpoc8m0.feedback JOIN l4tsmab3ywpoc8m0.accounts a on a.id_accounts = feedback.id_account WHERE id_product=?";
    private static final String SQL_SELECT_BY_ID =
            "SELECT id_feedback, feedback, evaluation, id_product, id_accounts, email, name, role, image, access, register_date FROM l4tsmab3ywpoc8m0.feedback JOIN l4tsmab3ywpoc8m0.accounts a on a.id_accounts = feedback.id_account WHERE id_feedback=?";
    private static final String SQL_DELETE_BY_ID =
            "DELETE FROM l4tsmab3ywpoc8m0.feedback WHERE id_feedback=?";
    private static final String SQL_CREATE =
            "INSERT INTO feedback SET feedback=?, evaluation=?, id_product=?, id_account=?, date=?";
    private final CustomConnectionPool connectionPool;

    public BaseFeedbackDao() {
        connectionPool = CustomConnectionPool.getInstance();
    }

    public BaseFeedbackDao(CustomConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public List<Feedback> findAll() throws DaoException {
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                feedbacks.add(getFeedbackFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return feedbacks;
    }

    @Override
    public List<Feedback> findAllByProductId(long id) throws DaoException {
        List<Feedback> feedbacks = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL_BY_PRODUCT_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                feedbacks.add(getFeedbackFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return feedbacks;
    }

    @Override
    public Optional<Feedback> findEntityById(Long id) throws DaoException {
        Optional<Feedback> optionalFeedback = Optional.empty();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                optionalFeedback = Optional.of(getFeedbackFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
        return optionalFeedback;
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_BY_ID)) {
            statement.setLong(1, id);
            return 1 == statement.executeUpdate();
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(Feedback feedback) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Feedback create(Feedback feedback) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statementUpdate = connection.prepareStatement(SQL_CREATE, Statement.RETURN_GENERATED_KEYS)) {
            setStatementParameters(statementUpdate,
                    feedback.getFeedback(),
                    feedback.getEvaluation(),
                    feedback.getIdProduct(),
                    feedback.getUser().getId(),
                    feedback.getDate().getTime());
            statementUpdate.executeUpdate();
            return feedback;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    private Feedback getFeedbackFromResultSet(ResultSet resultSet) throws SQLException {
        return Feedback
                .builder()
                .id(resultSet.getLong(DataBaseColumn.ID_FEEDBACK))
                .feedback(resultSet.getString(DataBaseColumn.FEEDBACK))
                .evaluation(resultSet.getByte(DataBaseColumn.FEEDBACK_EVALUATION))
                .idProduct(resultSet.getLong(DataBaseColumn.FEEDBACK_ID_PRODUCT))
                .user(User
                        .builder()
                        .id(resultSet.getLong(DataBaseColumn.ID_ACCOUNT))
                        .name(resultSet.getString(DataBaseColumn.ACCOUNT_NAME))
                        .email(resultSet.getString(DataBaseColumn.ACCOUNT_EMAIL))
                        .imageName(resultSet.getString(DataBaseColumn.ACCOUNT_IMAGE))
                        .registerDate(new Date(resultSet.getLong(DataBaseColumn.ACCOUNT_REGISTER_DATE)))
                        .access(TypeStatus.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ACCESS)))
                        .role(TypeRole.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ROLE)))
                        .build())
                .date(new Date(resultSet.getLong(DataBaseColumn.DATE)))
                .build();
    }

}
