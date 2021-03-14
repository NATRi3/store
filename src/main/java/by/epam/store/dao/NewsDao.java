package by.epam.store.dao;

import by.epam.store.entity.News;
import by.epam.store.exception.DaoException;

import java.util.List;

public interface NewsDao extends BaseDao<News> {
    List<News> findFreshNews(int count) throws DaoException;

    List<News> findSortNews(String typeSort, int begin, int count) throws DaoException;

    boolean changeImage(long id, String image) throws DaoException;
}
