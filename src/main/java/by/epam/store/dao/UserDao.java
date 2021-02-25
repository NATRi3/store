package by.epam.store.dao;

import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    boolean isEmailExists(String email) throws DaoException;

    Optional<User> findEntityByEmailAndPassword(String email, String pass) throws DaoException;

    boolean delete(String email, String password) throws DaoException;

    User createUser(User user, String password) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    void updatePassword(User user, String password) throws DaoException;

    List<User> findUserByRoleAndStatus(TypeStatus status, int begin) throws DaoException;

    boolean changeStatus(long id, TypeStatus statusFrom, TypeStatus statusTo) throws DaoException;
}
