package by.epam.store.model.service;

import by.epam.store.exception.ServiceException;
import by.epam.store.annotation.Dependency;

/**
 * The interface Image service.
 */
@Dependency
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
