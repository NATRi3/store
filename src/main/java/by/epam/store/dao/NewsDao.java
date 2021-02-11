package by.epam.store.dao;

import by.epam.store.entity.News;
import by.epam.store.exception.DaoException;

import java.util.List;

public interface NewsDao {
    public List<News> getFreshNews(int count) throws DaoException;

    List<News> getSortNews(String typeSort, int begin, int count) throws DaoException;
}
