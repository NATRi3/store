package by.epam.store.service.impl;

import by.epam.store.dao.impl.UserDao;
import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeAccess;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.SendMail;
import by.epam.store.validator.UserValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class UserService implements by.epam.store.service.UserService {
    private static final Logger log = LogManager.getLogger(UserService.class);
    private static final UserDao userDao = new UserDao();
    public static final SendMail sendMail = new SendMail();

    @Override
    public boolean activate(String code) throws ServiceException {
        try {
            long id = Long.parseLong(code);
            User activeUser;
            Optional<User> optionalUser = userDao.findEntityById(id);
            if(optionalUser.isPresent()){
                activeUser = optionalUser.get();
                activeUser.setAccess(TypeAccess.ACTIVE);
                userDao.update(activeUser);
                log.info(code + "ACTIVATE");
                return true;
            }
            return false;
        } catch (DaoException e) {
            log.error("Failed activation attempt");
            throw new ServiceException(e);
        }
    }

    @Override
    public User login(String email, String password) throws ServiceException {
        User user;
        if (!(UserValidator.isEmailValid(email))||!(UserValidator.isPasswordValid(password))){
            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            Optional<User> optionalUser = userDao.findEntityByEmailAndPassword(email, encryption(password));
            if(!optionalUser.isPresent()){
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_WRONG_EMAIL_OR_PASS);
            }
            user = optionalUser.get();
        } catch (DaoException e) {
            log.info("Failed login, wrong email or password " + email);
            throw new ServiceException(e);
        }
        return user;
    }

    @Override
    public User register(String name, String email, String password, String repeatPassword) throws ServiceException {
        User user;
        if (!(UserValidator.isEmailValid(email))||!(UserValidator.isPasswordValid(password))){
            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            if(userDao.isEmailExists(email)) {
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_EMAIL_EXIST);
            }
            user = new User(0, name, email, TypeRole.CLIENT);
            userDao.createUser(user, encryption(password));
            sendMail.sendActivationMailTo(email, user.getId());
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
        return user;
    }


    @Override
    public boolean deleteUser(String email, String password) throws ServiceException {
        try {
            return userDao.delete(email,password);
        } catch (DaoException e) {
            log.error("Failed delete attempt");
            throw new ServiceException();
        }
    }

    @Override
    public User findUserById(long id) throws ServiceException {
        User resultUser;
        try {
            Optional<User> optionalUser = userDao.findEntityById(id);
            if(!optionalUser.isPresent()){
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_USER_NOT_FOUNT);
            }
            resultUser = optionalUser.get();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return resultUser;
    }

    @Override
    public User updateImage(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e);
        }
    }

    private String encryption(String pass){
        return DigestUtils.md5Hex(pass);
    }
}
