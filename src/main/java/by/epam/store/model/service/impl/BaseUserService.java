package by.epam.store.model.service.impl;

import by.epam.store.model.dao.UserDao;
import by.epam.store.model.entity.TypeRole;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.model.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.UserService;
import by.epam.store.annotation.Autowired;
import by.epam.store.annotation.Bean;
import by.epam.store.util.MessageKey;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.model.validator.NumberValidator;
import by.epam.store.model.validator.TypeValidator;
import by.epam.store.model.validator.UserValidator;
import by.epam.store.model.validator.ValidationFields;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Base user service.
 */
@Bean
public class BaseUserService implements UserService {
    private final static Logger log = LogManager.getLogger(BaseUserService.class);
    private UserDao userDao;

    /**
     * Instantiates a new Base user service.
     */
    public BaseUserService() {

    }

    /**
     * Instantiates a new Base user service.
     *
     * @param BASE_USER_DAO the base user dao
     */
    public BaseUserService(UserDao BASE_USER_DAO) {
        this.userDao = BASE_USER_DAO;
    }

    /**
     * Sets base user dao.
     *
     * @param userDao the base user dao
     */
    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public String activate(String code) throws ServiceException {
        if (!NumberValidator.isLongValid(code)) {
            return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        }
        try {
            long id = Long.parseLong(code);
            if (userDao.changeStatus(id, TypeStatus.NONACTIVE, TypeStatus.ACTIVE)) {
                log.info(code + " ACTIVATE ACCOUNT");
                return MessageKey.SUCCESSFUL_ACTIVATION;
            } else {
                return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<String> login(User user, String password) throws ServiceException {
        Optional<String> resultMessage = Optional.empty();
        try {
            Optional<User> optionalUser =
                    userDao.findEntityByEmailAndPassword(user.getEmail(), encryption(password));
            if (optionalUser.isPresent()) {
                switch (optionalUser.get().getAccess()) {
                    case ACTIVE: {
                        User dataBaseUser = optionalUser.get();
                        user.setParametersForm(dataBaseUser);
                        break;
                    }
                    case BLOCKED: {
                        resultMessage = Optional.of(MessageKey.ERROR_MESSAGE_USER_BLOCKED);
                        break;
                    }
                    case NONACTIVE: {
                        resultMessage = Optional.of(MessageKey.ERROR_MESSAGE_USER_NONACTIVE);
                        break;
                    }
                    default: {
                        resultMessage = Optional.of(MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT);
                        break;
                    }
                }
            } else {
                resultMessage = Optional.of(MessageKey.ERROR_MESSAGE_WRONG_EMAIL_OR_PASS);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return resultMessage;
    }

    @Override
    public Optional<String> registerClient(Map<String, String[]> parameters) throws ServiceException {
        if (!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.EMAIL) ||
                !parameters.containsKey(RequestParameterAndAttribute.NAME) ||
                !parameters.containsKey(RequestParameterAndAttribute.PASSWORD)) {
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            String email = parameters.get(RequestParameterAndAttribute.EMAIL)[0];
            String name = parameters.get(RequestParameterAndAttribute.NAME)[0];
            String password = parameters.get(RequestParameterAndAttribute.PASSWORD)[0];
            if (!userDao.isEmailExists(email)) {
                User user;
                user = User.builder()
                        .name(name)
                        .email(email)
                        .role(TypeRole.CLIENT)
                        .access(TypeStatus.NONACTIVE)
                        .build();
                user = userDao.createUser(user, encryption(password));
                if(user.getId()==0){
                    throw new ServiceException("DB problems");
                }
                SendMail.sendActivationMailTo(email, user.getId());
                return Optional.empty();
            } else {
                parameters.remove(RequestParameterAndAttribute.EMAIL);
                return Optional.of(MessageKey.ERROR_MESSAGE_EMAIL_EXIST);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }


    @Override
    public Optional<User> findUserById(long id) throws ServiceException {
        try {
            return userDao.findEntityById(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public boolean updateById(User user) throws ServiceException {
        if (!UserValidator.isUserValid(user)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            return userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<String> changePasswordSendForgotMailMessage(String email) throws ServiceException {
        try {
            if (ValidationFields.EMAIL.isValid(email)) {
                Optional<User> optionalUser = userDao.findUserByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    String newPassword = generatePassword();
                    userDao.updatePassword(user, encryption(newPassword));
                    SendMail.sendForgotPasswordMessage(email, newPassword);
                    return Optional.empty();
                } else {
                    return Optional.of(MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT);
                }
            }
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findUsersByRoleAndStatus(String status, String begin) throws ServiceException {
        if (!NumberValidator.isLongValid(begin) ||
                !TypeValidator.isTypeStatus(status.toUpperCase())) {
            log.error(status + begin);
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            TypeStatus userStatus = TypeStatus.valueOf(status.toUpperCase());
            int beginPagination = Integer.parseInt(begin);
            return userDao.findUserByRoleAndStatus(userStatus, beginPagination);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeStatusFromTo(String id, String statusFrom, String statusTo) throws ServiceException {
        if (!NumberValidator.isLongValid(id) ||
                !TypeValidator.isTypeStatus(statusFrom) ||
                !TypeValidator.isTypeStatus(statusTo)) {
            return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        }
        String resultMessage = MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        try {
            long idUser = Long.parseLong(id);
            TypeStatus typeStatusFrom = TypeStatus.valueOf(statusFrom);
            TypeStatus typeStatusTo = TypeStatus.valueOf(statusTo);
            if (userDao.changeStatus(idUser, typeStatusFrom, typeStatusTo)) {
                resultMessage = MessageKey.SUCCESSFUL_CHANGE_STATUS;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return resultMessage;
    }

    @Override
    public Optional<String> registerAdmin(Map<String, String[]> parameters) throws ServiceException {
        if (!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.EMAIL) ||
                !parameters.containsKey(RequestParameterAndAttribute.NAME) ||
                !parameters.containsKey(RequestParameterAndAttribute.PASSWORD)) {
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            String email = parameters.get(RequestParameterAndAttribute.EMAIL)[0];
            String name = parameters.get(RequestParameterAndAttribute.NAME)[0];
            String password = parameters.get(RequestParameterAndAttribute.PASSWORD)[0];
            if (!userDao.isEmailExists(email)) {
                User user;
                user = User.builder()
                        .name(name)
                        .email(email)
                        .role(TypeRole.ADMIN)
                        .access(TypeStatus.ACTIVE)
                        .build();
                userDao.createUser(user, encryption(password));
                return Optional.empty();
            } else {
                parameters.remove(RequestParameterAndAttribute.EMAIL);
                return Optional.of(MessageKey.ERROR_MESSAGE_EMAIL_EXIST);
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String changePassword(Map<String, String[]> parameters) throws ServiceException {
        if(!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.PASSWORD) ||
                !parameters.containsKey(RequestParameterAndAttribute.ID_USER) ||
                !parameters.containsKey(RequestParameterAndAttribute.CHANGE_PASSWORD_OLD)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        String oldPassword = parameters.get(RequestParameterAndAttribute.CHANGE_PASSWORD_OLD)[0];
        String newPassword = parameters.get(RequestParameterAndAttribute.PASSWORD)[0];
        long userId = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_USER)[0]);
        try {
            if(userDao.changePassword(userId,newPassword,oldPassword)) {
                return MessageKey.SUCCESSFUL_CHANGE;
            } else {
                parameters.remove(RequestParameterAndAttribute.CHANGE_PASSWORD_OLD);
                return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
        if (!NumberValidator.isLongValid(id)) {
            return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        }
        String message;
        try {
            if (userDao.changeImageById(Long.parseLong(id), imageName)) {
                message = MessageKey.SUCCESSFUL_CHANGE_IMAGE;
            } else {
                message = MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
            }
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
        return message;
    }

    private String encryption(String pass) {
        return DigestUtils.md5Hex(pass);
    }

    private String generatePassword() {
        return RandomStringUtils.random(8, 0, 27, true, true, "QqWwEeRrTtSsHhMmCc123456789".toCharArray());
    }
}
