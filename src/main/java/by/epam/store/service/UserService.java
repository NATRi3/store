package by.epam.store.service;

import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService extends ImageService {

    String activate(String code) throws ServiceException;

    Optional<String> login(User user, String password) throws ServiceException;

    Optional<String> registerClient(Map<String, String> parameters) throws ServiceException;

    Optional<User> findUserById(long id) throws ServiceException;

    boolean updateById(User user) throws ServiceException;

    Optional<String> changePasswordSendForgotMailMessage(String email) throws ServiceException;

    List<User> findUsersByRoleAndStatus(String status, String begin) throws ServiceException;

    String changeStatusFromTo(String id, String statusFrom, String statusTo) throws ServiceException;

    Optional<String> registerAdmin(Map<String, String> parameters) throws ServiceException;
}
