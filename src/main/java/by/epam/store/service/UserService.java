package by.epam.store.service;

import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

import java.util.List;

public interface UserService {
    boolean activate(String code)throws ServiceException;
    User login(String email, String password) throws ServiceException;
    User registerClient(String email, String password, String name, String repeatPassword) throws ServiceException;
    boolean deleteUser(String email, String password) throws ServiceException;
    User findUserById(long id) throws ServiceException;
    boolean updateById(User user) throws ServiceException;
    void changePasswordSendForgotMailMessage(String email) throws ServiceException;
    String changeAvatar(User user, List<FileItem> fileItems) throws ServiceException;
}
