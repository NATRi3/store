package by.epam.store.service.impl;

import by.epam.store.dao.DaoCreator;
import by.epam.store.dao.impl.UserDao;
import by.epam.store.entity.TypeRole;
import by.epam.store.entity.TypeStatus;
import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.UserService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.SendMail;
import by.epam.store.validator.FormValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseUserService implements UserService {
    private static final UserDao userDao = DaoCreator.getInstance().getUserDao();
    private final static Logger log = LogManager.getLogger(BaseUserService.class);

    @Override
    public String activate(String code) throws ServiceException {
        try {
            long id = Long.parseLong(code);
            if (userDao.changeStatus(id, TypeStatus.NONACTIVE, TypeStatus.ACTIVE)) {
                log.info(code + "ACTIVATE");
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
    public Optional<String> registerClient(Map<String, String> parameters) throws ServiceException {
        try {
            String email = parameters.get(RequestParameterAndAttribute.EMAIL);
            String name = parameters.get(RequestParameterAndAttribute.NAME);
            String password = parameters.get(RequestParameterAndAttribute.PASSWORD);
            if (!userDao.isEmailExists(email)) {
                User user;
                user = new User(name, email, TypeRole.CLIENT);
                userDao.createUser(user, encryption(password));
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
        try {
            return userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<String> changePasswordSendForgotMailMessage(String email) throws ServiceException {
        try {
            if (FormValidator.isEmailValid(email)) {
                Optional<User> optionalUser = userDao.findUserByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    String newPassword = generatePassword();
                    userDao.updatePassword(user, encryption(newPassword));
                    SendMail.sendForgotPasswordMessage(email, newPassword);
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
    public Optional<String> registerAdmin(Map<String, String> parameters) throws ServiceException {
        return Optional.empty();
    }

    private String encryption(String pass) {
        return DigestUtils.md5Hex(pass);
    }

    private String generatePassword() {
        return RandomStringUtils.random(8, 0, 27, true, true, "QqWwEeRrTtSsHhMmCc123456789".toCharArray());
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
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
}
