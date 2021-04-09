package by.epam.store.model.service;

import by.epam.store.model.entity.ProductCollection;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * The interface Collection service.
 */
public interface CollectionService {

    /**
     * Find all product collections by status list.
     *
     * @param status the status
     * @return the list
     * @throws ServiceException the service exception
     */
    List<ProductCollection> findAllProductCollectionsByStatus(String status) throws ServiceException;

    /**
     * Find all product collections list.
     *
     * @return the list
     * @throws ServiceException the service exception
     */
    List<ProductCollection> findAllProductCollections() throws ServiceException;

    /**
     * Create collection string.
     *
     * @param parameters the parameters
     * @return the string
     * @throws ServiceException the service exception
     */
    String createCollection(Map<String, String[]> parameters) throws ServiceException;

    /**
     * Change status string.
     *
     * @param id     the id
     * @param status the status
     * @return the string
     * @throws ServiceException the service exception
     */
    String changeStatus(String id, TypeStatus status) throws ServiceException;

    /**
     * Change info string.
     *
     * @param parameters the parameters
     * @return the string
     * @throws ServiceException the service exception
     */
    String changeInfo(Map<String, String[]> parameters) throws ServiceException;
}
