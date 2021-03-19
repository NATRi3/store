package by.epam.store.service.impl;

import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.UserService;
import by.epam.store.validator.UserValidator;
import by.epam.store.util.MessageKey;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Validation user service.
 */
public class ValidationUserService implements UserService {
    private static final Logger log = LogManager.getLogger(ValidationUserService.class);

    private final BaseUserService baseUserService;

    /**
     * Instantiates a new Validation user service.
     *
     * @param baseUserService the base user service
     */
    public ValidationUserService(BaseUserService baseUserService) {
        this.baseUserService = baseUserService;
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
        if (NumberValidator.isLongValid(id)) {
            return baseUserService.changeImage(id, imageName);
        } else {
            return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        }
    }

    @Override
    public String activate(String code) throws ServiceException {
        if (NumberValidator.isLongValid(code)) {
            return baseUserService.activate(code);
        } else {
            return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        }
    }

    @Override
    public Optional<String> login(User user, String password) throws ServiceException {
        return baseUserService.login(user, password);
    }

    @Override
    public Optional<String> registerClient(Map<String, String> parameters) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return baseUserService.registerClient(parameters);
        } else {
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
    }

    @Override
    public Optional<User> findUserById(long id) throws ServiceException {
        return baseUserService.findUserById(id);
    }

    @Override
    public boolean updateById(User user) throws ServiceException {
        if (UserValidator.isUserValid(user)) {
            return baseUserService.updateById(user);
        } else {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
    }

    @Override
    public Optional<String> changePasswordSendForgotMailMessage(String email) throws ServiceException {
        if (FormValidator.isEmailValid(email)) {
            return baseUserService.changePasswordSendForgotMailMessage(email);
        } else {
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
    }

    @Override
    public List<User> findUsersByRoleAndStatus(String status, String begin) throws ServiceException {
        if (NumberValidator.isLongValid(begin) ||
                TypeValidator.isTypeStatus(status.toUpperCase())) {
            return baseUserService.findUsersByRoleAndStatus(status, begin);
        } else {
            log.error(status + begin);
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
    }

    @Override
    public String changeStatusFromTo(String id, String statusFrom, String statusTo) throws ServiceException {
        if (NumberValidator.isLongValid(id) ||
                TypeValidator.isTypeStatus(statusFrom) ||
                TypeValidator.isTypeStatus(statusTo)) {
            return baseUserService.changeStatusFromTo(id, statusFrom, statusTo);
        } else {
            return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        }
    }

    @Override
    public Optional<String> registerAdmin(Map<String, String> parameters) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return baseUserService.registerAdmin(parameters);
        } else {
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
    }

    @Override
    public String changePassword(Map<String, String> parameters) throws ServiceException {
        if(FormValidator.isFormValid(parameters)) {
            return baseUserService.changePassword(parameters);
        } else {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
    }
}
