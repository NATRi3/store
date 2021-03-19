package by.epam.store.dao.impl;

import by.epam.store.entity.TypeRole;
import by.epam.store.entity.TypeStatus;
import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


/**
 * The type Base user dao.
 */
public class BaseUserDao implements by.epam.store.dao.UserDao {
    private final static Logger log = LogManager.getLogger(BaseUserDao.class);
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_SELECT_ID_BY_EMAIL = "SELECT id_accounts, name, email, register_date, image, access, role FROM l4tsmab3ywpoc8m0.accounts WHERE email=?";
    private static final String SQL_SELECT_ALL =
            "SELECT id_accounts, name, email, register_date, image, access, role FROM l4tsmab3ywpoc8m0.accounts";
    private static final String SQL_SELECT_EMAIL_BY_EMAIL = "SELECT email FROM l4tsmab3ywpoc8m0.accounts WHERE email = ?";
    private static final String SQL_SELECT_BY_EMAIL_AND_PASSWORD = "SELECT id_accounts, name, email, register_date, image, access, role FROM l4tsmab3ywpoc8m0.accounts WHERE binary email = ? AND binary password = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT name,email,register_date, image,id_accounts,access,role FROM l4tsmab3ywpoc8m0.accounts WHERE id_accounts = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO l4tsmab3ywpoc8m0.accounts (`email`, `name`, `register_date`, `password`,`role`,`access`)VALUES (?,?,?,?,?,?);";
    private static final String SQL_DELETE_USER = "DELETE FROM l4tsmab3ywpoc8m0.accounts WHERE email=? AND password=?";
    private static final String SQL_UPDATE = "UPDATE l4tsmab3ywpoc8m0.accounts SET email = ?, name =?, register_date=?, image=?, access = ?, role = ? WHERE id_accounts = ? LIMIT 1;";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE l4tsmab3ywpoc8m0.accounts SET password = ? WHERE id_accounts = ? LIMIT 1";
    private static final String SQL_SELECT_BY_ROLE_STATUS = "SELECT id_accounts, name, email, register_date, image, access, role FROM l4tsmab3ywpoc8m0.accounts WHERE access=?  LIMIT 10 OFFSET ?";
    private static final String SQL_SET_STATUS_FROM_TO = "UPDATE l4tsmab3ywpoc8m0.accounts SET access=? WHERE id_accounts=? and access=? LIMIT 1";
    private static final String SQL_SET_IMAGE_BY_ID = "UPDATE l4tsmab3ywpoc8m0.accounts SET image=? WHERE id_accounts=? LIMIT 1";
    private static final String SQL_SET_PASSWORD_BY_ID_PASSWORD = "UPDATE l4tsmab3ywpoc8m0.accounts SET password=? WHERE id_accounts=? AND password=? LIMIT 1";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> result = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                result.add(createUserFormResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.info(e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public boolean isEmailExists(String email) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EMAIL_BY_EMAIL)) {
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<User> findEntityByEmailAndPassword(String email, String pass) throws DaoException {
        Optional<User> result = Optional.empty();
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_EMAIL_AND_PASSWORD)) {
            statement.setString(1, email);
            statement.setString(2, pass);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = Optional.of(createUserFormResultSet(resultSet));
            }
        } catch (SQLException e) {
            log.info(e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            Optional<User> result = Optional.empty();
            if (resultSet.next()) {
                result = Optional.of(createUserFormResultSet(resultSet));
            }
            return result;
        } catch (SQLException e) {
            log.info(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(String email, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)) {
            statement.setString(1, email);
            statement.setString(2, password);
            return statement.executeUpdate() == 1;

        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public User createUser(User user, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setLong(3, new Date().getTime());
            statement.setString(4, password);
            statement.setString(5, user.getRole().name());
            statement.setString(6,user.getAccess().name());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(1));
            }
            return user;
        } catch (SQLException ex) {
            log.error(ex);
            throw new DaoException(ex);
        }
    }

    @Override
    public Optional<User> findUserByEmail(String email) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ID_BY_EMAIL)) {
            Optional<User> optionalUser = Optional.empty();
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                optionalUser = Optional.of(createUserFormResultSet(resultSet));
            }
            return optionalUser;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void updatePassword(User user, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            statement.setString(1, password);
            statement.setLong(2, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            log.error(ex);
            throw new DaoException(ex);
        }
    }

    @Override
    public List<User> findUserByRoleAndStatus(TypeStatus status, int begin) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ROLE_STATUS)) {
            statement.setString(1, status.toString());
            statement.setInt(2, begin);
            ResultSet resultSet = statement.executeQuery();
            List<User> resultUserFromDB = new ArrayList<>();
            while (resultSet.next()) {
                resultUserFromDB.add(createUserFormResultSet(resultSet));
            }
            return resultUserFromDB;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean changeStatus(long id, TypeStatus statusFrom, TypeStatus statusTo) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_STATUS_FROM_TO)) {
            statement.setString(1, statusTo.toString());
            statement.setLong(2, id);
            statement.setString(3, statusFrom.toString());
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean changeImageById(long id, String imageName) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_IMAGE_BY_ID)) {
            statement.setString(1, imageName);
            statement.setLong(2, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean changePassword(long userId, String newPassword, String oldPassword) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SET_PASSWORD_BY_ID_PASSWORD)) {
            statement.setString(1, newPassword);
            statement.setLong(2, userId);
            statement.setString(3, oldPassword);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean update(User user) throws DaoException {
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setLong(3, user.getRegisterDate().getTime());
            statement.setString(4, user.getImageName());
            statement.setString(5, user.getAccess().toString());
            statement.setString(6, user.getRole().toString());
            statement.setLong(7, user.getId());
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            log.error(ex);
            throw new DaoException(ex);
        }
    }

    @Override
    public User create(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    /**
     * Create user form result set.
     *
     * @param resultSet the result set
     * @return the user
     * @throws SQLException the sql exception
     */
    static User createUserFormResultSet(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt(DataBaseColumn.ID_ACCOUNT))
                .name(resultSet.getString(DataBaseColumn.ACCOUNT_NAME))
                .email(resultSet.getString(DataBaseColumn.ACCOUNT_EMAIL))
                .imageName(resultSet.getString(DataBaseColumn.ACCOUNT_IMAGE))
                .access(TypeStatus.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ACCESS)))
                .role(TypeRole.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ROLE)))
                .registerDate(new Date(resultSet.getLong(DataBaseColumn.ACCOUNT_REGISTER_DATE)))
                .build();
    }
}
