package by.epam.store.model.dao;

import by.epam.store.model.entity.News;
import by.epam.store.exception.DaoException;

import java.util.List;

/**
 * The interface News dao.
 */
public interface NewsDao extends BaseDao<News> {
    /**
     * Find fresh news list.
     *
     * @param count the count
     * @return the list
     * @throws DaoException the dao exception
     */
    List<News> findFreshNews(int count) throws DaoException;

    /**
     * Find sort news list.
     *
     * @param typeSort the type sort
     * @param begin    the begin
     * @param count    the count
     * @return the list
     * @throws DaoException the dao exception
     */
    List<News> findSortNews(String typeSort, int begin, int count) throws DaoException;

    /**
     * Change image boolean.
     *
     * @param id    the id
     * @param image the image
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean changeImage(long id, String image) throws DaoException;
}
