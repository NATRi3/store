package by.epam.store.model.dao;

import by.epam.store.model.entity.TypeStatus;
import by.epam.store.model.entity.User;
import by.epam.store.exception.DaoException;

import java.util.List;
import java.util.Optional;

/**
 * The interface User dao.
 */
public interface UserDao extends BaseDao<User> {
    /**
     * Is email exists boolean.
     *
     * @param email the email
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean isEmailExists(String email) throws DaoException;

    /**
     * Find entity by email and password optional.
     *
     * @param email the email
     * @param pass  the pass
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findEntityByEmailAndPassword(String email, String pass) throws DaoException;

    /**
     * Delete boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean delete(String email, String password) throws DaoException;

    /**
     * Create user user.
     *
     * @param user     the user
     * @param password the password
     * @return the user
     * @throws DaoException the dao exception
     */
    User createUser(User user, String password) throws DaoException;

    /**
     * Find user by email optional.
     *
     * @param email the email
     * @return the optional
     * @throws DaoException the dao exception
     */
    Optional<User> findUserByEmail(String email) throws DaoException;

    /**
     * Update password.
     *
     * @param user     the user
     * @param password the password
     * @throws DaoException the dao exception
     */
    void updatePassword(User user, String password) throws DaoException;

    /**
     * Find user by role and status list.
     *
     * @param status the status
     * @param begin  the begin
     * @return the list
     * @throws DaoException the dao exception
     */
    List<User> findUserByRoleAndStatus(TypeStatus status, int begin) throws DaoException;

    /**
     * Change status boolean.
     *
     * @param id         the id
     * @param statusFrom the status from
     * @param statusTo   the status to
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean changeStatus(long id, TypeStatus statusFrom, TypeStatus statusTo) throws DaoException;

    /**
     * Change image by id boolean.
     *
     * @param id        the id
     * @param imageName the image name
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean changeImageById(long id, String imageName) throws DaoException;

    /**
     * Change password boolean.
     *
     * @param userId      the user id
     * @param newPassword the new password
     * @param oldPassword the old password
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean changePassword(long userId, String newPassword, String oldPassword) throws DaoException;
}
