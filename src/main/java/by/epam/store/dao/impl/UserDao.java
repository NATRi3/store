package by.epam.store.dao.impl;

import by.epam.store.dao.BaseDao;
import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeAccess;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.exception.DaoException;
import by.epam.store.pool.CustomConnectionPool;
import by.epam.store.util.MessageErrorKey;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Log4j2
public class UserDao implements BaseDao<User>, by.epam.store.dao.UserDao {
    private static final CustomConnectionPool connectionPool = CustomConnectionPool.getInstance();
    private static final String SQL_SELECT_ALL =
            "SELECT id_accounts, name, email,register_date, image, access, role FROM store.accounts";
    private static final String SQL_SELECT_EMAIL_BY_EMAIL ="SELECT email FROM store.accounts WHERE email = ?";
    private static final String SQL_SELECT_BY_EMAIL_AND_PASSWORD ="SELECT id_accounts, name, email, register_date, image, access, role FROM store.accounts WHERE email = ? AND password = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT name,email,register_date, image,id_accounts,access,role FROM store.accounts WHERE id_accounts = ?";
    private static final String SQL_INSERT_USER = "INSERT INTO store.accounts (`email`, `name`, `register_date`, `password`,`role`)VALUES (?,?,?,?,?);";
    private static final String SQL_DELETE_USER ="DELETE FROM store.accounts WHERE email=? AND password=?";
    private static final String SQL_UPDATE = "UPDATE store.accounts SET email = ?, name =?, register_date=?, image=?, access = ?, role = ? WHERE id_accounts = ? LIMIT 1;";

    @Override
    public List<User> findAll() throws DaoException {
        List<User> result = new ArrayList<>();
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                int idUser = resultSet.getInt(DataBaseColumn.ID_ACCOUNT);
                String name = resultSet.getString(DataBaseColumn.ACCOUNT_NAME);
                String email = resultSet.getString(DataBaseColumn.ACCOUNT_EMAIL);
                String image = resultSet.getString(DataBaseColumn.ACCOUNT_IMAGE);
                Date dateTime = new Date(resultSet.getLong(DataBaseColumn.ACCOUNT_REGISTER_DATE));
                TypeAccess access = TypeAccess.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ACCESS));
                TypeRole role = TypeRole.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ROLE));
                User user = new User(idUser,name,email,dateTime,image,access,role);
                result.add(user);
            }
        } catch (SQLException e) {
            log.info(e);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
        return result;
    }
    @Override
    public boolean isEmailExists(String email) throws DaoException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_EMAIL_BY_EMAIL)){
            statement.setString(1,email);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        }catch (SQLException e) {
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
    }
    @Override
    public Optional<User> findEntityByEmailAndPassword(String email, String pass) throws DaoException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_EMAIL_AND_PASSWORD)){
            Optional<User> result = Optional.empty();
            statement.setString(1,email);
            statement.setString(2,pass);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                int id = resultSet.getInt(DataBaseColumn.ID_ACCOUNT);
                String name = resultSet.getString(DataBaseColumn.ACCOUNT_NAME);
                String dbEmail = resultSet.getString(DataBaseColumn.ACCOUNT_EMAIL);
                String image = resultSet.getString(DataBaseColumn.ACCOUNT_IMAGE);
                Date dateTime = new Date(resultSet.getLong(DataBaseColumn.ACCOUNT_REGISTER_DATE));
                TypeAccess access = TypeAccess.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ACCESS));
                TypeRole role = TypeRole.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ROLE));
                result = Optional.of(new User(id, name, dbEmail, dateTime, image, access, role));
            }
            return result;
        }catch (SQLException e) {
            log.info(e);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
    }

    @Override
    public Optional<User> findEntityById(Long id) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)){
            statement.setLong(1,id);
            ResultSet resultSet = statement.executeQuery();
            Optional<User> result = Optional.empty();
            if(resultSet.next()) {
                long idUser = resultSet.getInt(DataBaseColumn.ID_ACCOUNT);
                String name = resultSet.getString(DataBaseColumn.ACCOUNT_NAME);
                String email = resultSet.getString(DataBaseColumn.ACCOUNT_EMAIL);
                String image = resultSet.getString(DataBaseColumn.ACCOUNT_IMAGE);
                Date dateTime = new Date(resultSet.getLong(DataBaseColumn.ACCOUNT_REGISTER_DATE));
                TypeAccess access = TypeAccess.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ACCESS));
                TypeRole role = TypeRole.valueOf(resultSet.getString(DataBaseColumn.ACCOUNT_ROLE));
                result = Optional.of(new User(idUser, name, email, dateTime, image, access, role));
            }
            return result;
        }catch (SQLException e) {
            log.info(e);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(String email, String password) throws DaoException {
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(SQL_DELETE_USER)){
            statement.setString(1, email);
            statement.setString(2,password);
            return statement.executeUpdate()==1;

        } catch (SQLException e) {
            log.error(e);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
    }

    @Override
    public User createUser(User user, String password) throws DaoException {
        try(Connection connection = getConnection();
            PreparedStatement statementUpdate = connection.prepareStatement(SQL_INSERT_USER);
            PreparedStatement statementSelect = connection.prepareStatement(SQL_SELECT_BY_EMAIL_AND_PASSWORD)){
            statementUpdate.setString(1,user.getEmail());
            statementUpdate.setString(2,user.getName());
            statementUpdate.setString(3,password);
            statementUpdate.setString(4,user.getRole().toString());
            statementUpdate.executeUpdate();
            ResultSet resultSet = statementUpdate.getGeneratedKeys();
            if(resultSet.next()) {
                user.setId(resultSet.getInt(DataBaseColumn.ID_ACCOUNT));
            }
            return user;
        }catch (SQLException ex) {
            log.error(ex);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
    }

    @Override
    public User update(User user) throws DaoException {
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)){
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getName());
            statement.setLong(3, user.getRegisterDate().getTime());
            statement.setString(4, user.getImageName());
            statement.setString(5, user.getAccess().toString());
            statement.setString(6, user.getRole().toString());
            statement.setLong(7, user.getId());
            statement.executeUpdate();
            return user;
        }catch (SQLException ex) {
            log.error(ex);
            throw new DaoException(MessageErrorKey.ERROR_MESSAGE_SERVER_PROBLEM);
        }
    }

    @Override
    public User create(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    private Connection getConnection() throws SQLException {
        return connectionPool.getConnection();
    }

}
