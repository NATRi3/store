package by.epam.store.service.impl;

import by.epam.store.entity.User;
import by.epam.store.entity.type.TypeRole;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.BaseService;
import by.epam.store.util.FileNameGenerator;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.util.SendMail;
import by.epam.store.validator.UserValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
public class UserService implements by.epam.store.service.UserService, BaseService {
    private static final String SAVE_DIR = "C:/projectImg/";
    private static final Set<String> FILE_TYPE = Set.of("image/jpg", "image/png", "image/gif", "image/jpeg");

    @Override
    public boolean activate(String code) throws ServiceException {
        try {
            long id = Long.parseLong(code);
            User activeUser;
            Optional<User> optionalUser = userDao.findEntityById(id);
            if(optionalUser.isPresent()){
                activeUser = optionalUser.get();
                activeUser.setAccess(TypeStatus.ACTIVE);
                userDao.update(activeUser);
                log.info(code + "ACTIVATE");
                return true;
            }
            return false;
        } catch (DaoException e) {
            log.error("Failed activation attempt");
            throw new ServiceException(e.getMessage());
        } catch (IllegalArgumentException e){
            log.error(e);
            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_USER_NOT_FOUNT);
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
            throw new ServiceException(e.getMessage());
        }
        return user;
    }

    @Override
    public User registerClient(String name, String email, String password, String repeatPassword) throws ServiceException {
        User user;
        if (!(UserValidator.isEmailValid(email))||
                !(UserValidator.isPasswordValid(password))||
                !(password.equals(repeatPassword))){
            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            if(userDao.isEmailExists(email)) {
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_EMAIL_EXIST);
            }
            user = new User(name, email,TypeRole.CLIENT);
            userDao.createUser(user, encryption(password));
            SendMail.sendActivationMailTo(email, user.getId());
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
            throw new ServiceException(e.getMessage());
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
            throw new ServiceException(e.getMessage());
        }
        return resultUser;
    }

    @Override
    public boolean updateById(User user) throws ServiceException {
        try {
            return userDao.update(user);
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changePasswordSendForgotMailMessage(String email) throws ServiceException {
        if(!UserValidator.isEmailValid(email)){
            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            Optional<User> optionalUser = userDao.findUserByEmail(email);
            if(optionalUser.isPresent()){
                User user = optionalUser.get();
                String newPassword = generatePassword();
                userDao.updatePassword(user,encryption(newPassword));
                SendMail.sendForgotPasswordMessage(email,newPassword);
            } else {
                log.info("User not found " + email);
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_USER_NOT_FOUNT);
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String changeAvatar(User user, List<FileItem> fileItems) throws ServiceException {
        String nameNewFile = "defaultAvatar.jpg";
        try {
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {
                    if (FILE_TYPE.contains(fileItem.getContentType())) {
                        nameNewFile = FileNameGenerator.generate(fileItem.getName());
                        File file = new File(SAVE_DIR +
                                nameNewFile);
                        fileItem.write(file);
                    } else {
                        throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_WRONG_FILE_TYPE);
                    }
                    user.setImageName(nameNewFile);
                    userDao.update(user);
                }
            }
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(MessageErrorKey.ERROR_UPLOAD_FILE);
        }
        return nameNewFile;
    }

    private String encryption(String pass){
        return DigestUtils.md5Hex(pass);
    }

    private String generatePassword(){
        return RandomStringUtils.random(8,0,27,true,true,"QqWwEeRrTtSsHhMmCc123456789".toCharArray());
    }
}
