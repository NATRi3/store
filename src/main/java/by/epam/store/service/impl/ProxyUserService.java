package by.epam.store.service.impl;

import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.UserService;
import by.epam.store.util.MessageKey;
import by.epam.store.validator.NumberValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProxyUserService implements UserService {

    private final by.epam.store.service.impl.UserService userService;

    public ProxyUserService(by.epam.store.service.impl.UserService userService) {
        this.userService = userService;
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
        if (NumberValidator.isLongValid(id)) {
            return userService.changeImage(id,imageName);
        } else {
            return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        }
    }

    @Override
    public String activate(String code) throws ServiceException {
        return null;
    }

    @Override
    public Optional<String> login(User user, String password) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public Optional<String> registerClient(Map<String, String> parameters) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public User findUserById(long id) throws ServiceException {
        return null;
    }

    @Override
    public boolean updateById(User user) throws ServiceException {
        return false;
    }

    @Override
    public Optional<String> changePasswordSendForgotMailMessage(String email) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<User> findUsersByRoleAndStatus(String status, String begin) throws ServiceException {
        return null;
    }

    @Override
    public String changeStatusFromTo(String id, String statusFrom, String statusTo) throws ServiceException {
        return null;
    }

    @Override
    public Optional<String> registerAdmin(Map<String, String> parameters) throws ServiceException {
        return Optional.empty();
    }
}
