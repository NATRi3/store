package by.epam.store.service;

import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;

import java.util.Optional;

public interface UserService {
    boolean activate(String code)throws ServiceException;
    User login(String email, String password) throws ServiceException;
    User register(String email, String password, String name, String repeatPassword) throws ServiceException;
    boolean deleteUser(String email, String password) throws ServiceException;
    User findUserById(long id) throws ServiceException;
    User updateImage(User user) throws ServiceException;
}
