package by.epam.store.service;

import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface User service.
 */
public interface UserService extends ImageService {

    /**
     * Activate string.
     *
     * @param code the code
     * @return the string
     * @throws ServiceException the service exception
     */
    String activate(String code) throws ServiceException;

    /**
     * Login optional.
     *
     * @param user     the user
     * @param password the password
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<String> login(User user, String password) throws ServiceException;

    /**
     * Register client optional.
     *
     * @param parameters the parameters
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<String> registerClient(Map<String, String> parameters) throws ServiceException;

    /**
     * Find user by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<User> findUserById(long id) throws ServiceException;

    /**
     * Update by id boolean.
     *
     * @param user the user
     * @return the boolean
     * @throws ServiceException the service exception
     */
    boolean updateById(User user) throws ServiceException;

    /**
     * Change password send forgot mail message optional.
     *
     * @param email the email
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<String> changePasswordSendForgotMailMessage(String email) throws ServiceException;

    /**
     * Find users by role and status list.
     *
     * @param status the status
     * @param begin  the begin
     * @return the list
     * @throws ServiceException the service exception
     */
    List<User> findUsersByRoleAndStatus(String status, String begin) throws ServiceException;

    /**
     * Change status from to string.
     *
     * @param id         the id
     * @param statusFrom the status from
     * @param statusTo   the status to
     * @return the string
     * @throws ServiceException the service exception
     */
    String changeStatusFromTo(String id, String statusFrom, String statusTo) throws ServiceException;

    /**
     * Register admin optional.
     *
     * @param parameters the parameters
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<String> registerAdmin(Map<String, String> parameters) throws ServiceException;

    /**
     * Change password string.
     *
     * @param parameters the parameters
     * @return the string
     * @throws ServiceException the service exception
     */
    String changePassword(Map<String, String> parameters) throws ServiceException;
}
