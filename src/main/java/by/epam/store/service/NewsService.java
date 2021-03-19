package by.epam.store.service;

import by.epam.store.entity.News;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;

/**
 * The interface News service.
 */
public interface NewsService extends ImageService {

    /**
     * Find fresh news list.
     *
     * @param count the count
     * @return the list
     * @throws ServiceException the service exception
     */
    List<News> findFreshNews(String count) throws ServiceException;

    /**
     * Find sort news list.
     *
     * @param typeSort the type sort
     * @param begin    the begin
     * @return the list
     * @throws ServiceException the service exception
     */
    List<News> findSortNews(String typeSort, String begin) throws ServiceException;

    /**
     * Delete news string.
     *
     * @param id the id
     * @return the string
     * @throws ServiceException the service exception
     */
    String deleteNews(String id) throws ServiceException;

    /**
     * Create news string.
     *
     * @param parameters the parameters
     * @return the string
     * @throws ServiceException the service exception
     */
    String createNews(Map<String, String> parameters) throws ServiceException;

    String changeImage(String id, String imageName) throws ServiceException;

    /**
     * Change news string.
     *
     * @param parameters the parameters
     * @return the string
     * @throws ServiceException the service exception
     */
    String changeNews(Map<String, String> parameters) throws ServiceException;
}
