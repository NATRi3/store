package by.epam.store.dao;

import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;

import java.util.Optional;

public interface UserDao {
    boolean isEmailExists(String email) throws DaoException;

    Optional<User> findEntityByEmailAndPassword(String email, String pass) throws DaoException;

    boolean delete(String email, String password) throws DaoException;

    User createUser(User user, String password) throws DaoException;
}
