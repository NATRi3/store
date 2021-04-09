package by.epam.store.model.service;

import by.epam.store.exception.ServiceException;

/**
 * The interface Image service.
 */
public interface ImageService {

    /**
     * Change image string.
     *
     * @param id        the id
     * @param imageName the image name
     * @return the string
     * @throws ServiceException the service exception
     */
    String changeImage(String id, String imageName) throws ServiceException;

}
