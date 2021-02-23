package by.epam.store.service.impl;

import by.epam.store.dao.impl.UserDao;
import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.DaoCreator;
import by.epam.store.util.*;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserService implements by.epam.store.service.UserService {
    private static final UserDao userDao = DaoCreator.getInstance().getUserDao();
    private final static Logger log = LogManager.getLogger(UserService.class);
    @Override
    public String activate(String code) throws ServiceException {
        try {
            if(NumberValidator.isNumberValid(code)) {
                long id = Long.parseLong(code);
                User activeUser;
                Optional<User> optionalUser = userDao.findEntityById(id);
                if (optionalUser.isPresent()) {
                    activeUser = optionalUser.get();
                    if(!activeUser.getAccess().equals(TypeStatus.BLOCKED)) {
                        activeUser.setAccess(TypeStatus.ACTIVE);
                        userDao.update(activeUser);
                        log.info(code + "ACTIVATE");
                        return MessageKey.SUCCESSFUL_ACTIVATION;
                    } else {
                        return MessageKey.ERROR_MESSAGE_USER_BLOCKED;
                    }
                }
            }
            return MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        } catch (DaoException e) {
            log.error("Failed activation attempt");
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<String> login(User user, String password) throws ServiceException {
        Optional<String> resultMessage = Optional.empty();
        try {
            Optional<User> optionalUser =
                    userDao.findEntityByEmailAndPassword(user.getEmail(), encryption(password));
            if(optionalUser.isPresent()){
                User dataBaseUser = optionalUser.get();
                user.setParametersForm(dataBaseUser);
            } else {
                resultMessage = Optional.of(MessageKey.ERROR_MESSAGE_WRONG_EMAIL_OR_PASS);
            }
        } catch (DaoException e) {
            log.info(e);
            throw new ServiceException(e);
        }
        return resultMessage;
    }

    @Override
    public Optional<String> registerClient(Map<String, String> parameters) throws ServiceException {
        try {
            if(FormValidator.isFormValid(parameters)){
                String email = parameters.get(RequestParameter.EMAIL);
                String name = parameters.get(RequestParameter.NAME);
                String password = parameters.get(RequestParameter.PASSWORD);
                if(!userDao.isEmailExists(email)) {
                    User user;
                    user = new User(name, email,TypeRole.CLIENT);
                    userDao.createUser(user, encryption(password));
                    SendMail.sendActivationMailTo(email, user.getId());
                    return Optional.empty();
                }else {
                    parameters.remove(RequestParameter.EMAIL);
                    return Optional.of(MessageKey.ERROR_MESSAGE_EMAIL_EXIST);
                }
            } else {
                return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }


    @Override
    public User findUserById(long id) throws ServiceException {
        User resultUser;
        try {
            Optional<User> optionalUser = userDao.findEntityById(id);
            if(!optionalUser.isPresent()){
                throw new ServiceException(MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT);
            }
            resultUser = optionalUser.get();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return resultUser;
    }

    @Override
    public boolean updateById(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public Optional<String> changePasswordSendForgotMailMessage(String email) throws ServiceException {
        try {
            if(FormValidator.isEmailValid(email)) {
                Optional<User> optionalUser = userDao.findUserByEmail(email);
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    String newPassword = generatePassword();
                    userDao.updatePassword(user, encryption(newPassword));
                    SendMail.sendForgotPasswordMessage(email, newPassword);
                }else {
                    return Optional.of(MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT);
                }
            }
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> findUsersByRoleAndStatus(String role, String status, String begin) throws ServiceException {
        if(!NumberValidator.isNumberValid(begin)||
            !TypeValidator.isTypeStatus(status.toUpperCase())||
            !TypeValidator.isTypeRole(role.toUpperCase())){
            log.error(role+status+begin);
            throw new ServiceException("Invalid param");
        }
        try {
            TypeRole userRole = TypeRole.valueOf(role.toUpperCase());
            TypeStatus userStatus = TypeStatus.valueOf(status.toUpperCase());
            int beginPagination = Integer.parseInt(begin);
            return userDao.findUserByRoleAndStatus(userRole,userStatus,beginPagination);
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeStatus(String id, TypeStatus status) throws ServiceException {
        String resultMessage = MessageKey.ERROR_MESSAGE_USER_NOT_FOUNT;
        if(!NumberValidator.isNumberValid(id)) {
            try {
                Optional<User> optionalUser = userDao.findEntityById(Long.valueOf(id));
                if (optionalUser.isPresent()) {
                    User user = optionalUser.get();
                    user.setAccess(status);
                    userDao.update(user);
                    resultMessage = MessageKey.SUCCESSFUL_CHANGE_STATUS;
                }
            } catch (DaoException e) {
                log.error(e);
                throw new ServiceException(e);
            }
        }
        return resultMessage;
    }

    private String encryption(String pass){
        return DigestUtils.md5Hex(pass);
    }

    private String generatePassword(){
        return RandomStringUtils.random(8,0,27,true,true,"QqWwEeRrTtSsHhMmCc123456789".toCharArray());
    }
}
