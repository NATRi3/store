package by.epam.store.service;


import by.epam.store.entity.Product;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface Product service.
 */
public interface ProductService extends ImageService {

    /**
     * Find product by id optional.
     *
     * @param id the id
     * @return the optional
     * @throws ServiceException the service exception
     */
    Optional<Product> findProductById(String id) throws ServiceException;

    /**
     * Save product string.
     *
     * @param parameters the parameters
     * @return the string
     * @throws ServiceException the service exception
     */
    String saveProduct(Map<String, String> parameters) throws ServiceException;

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
     * Find product by collection and sort list.
     *
     * @param idCollection the id collection
     * @param typeSort     the type sort
     * @param status       the status
     * @param begin        the begin
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Product> findProductByCollectionAndSort(String idCollection, String typeSort, String status, String begin) throws ServiceException;

    /**
     * Find random product list.
     *
     * @param amount the amount
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Product> findRandomProduct(String amount) throws ServiceException;

    /**
     * Change product string.
     *
     * @param parameters the parameters
     * @return the string
     * @throws ServiceException the service exception
     */
    String changeProduct(Map<String, String> parameters) throws ServiceException;

    String changeImage(String id, String imageName) throws ServiceException;

    /**
     * Search product list.
     *
     * @param name the name
     * @return the list
     * @throws ServiceException the service exception
     */
    List<Product> searchProduct(String name) throws ServiceException;
}
