package by.epam.store.service;

import by.epam.store.exception.ServiceException;

public interface ImageService {

    String changeImage(String id, String imageName) throws ServiceException;

}
