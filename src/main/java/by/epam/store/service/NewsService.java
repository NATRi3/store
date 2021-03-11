package by.epam.store.service;

import by.epam.store.dao.impl.NewsDao;
import by.epam.store.entity.News;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface NewsService extends ImageService {
    static final NewsDao newsDao = DaoCreator.getInstance().getNewsDao();

    List<News> findFreshNews(String count) throws ServiceException;

    List<News> findSortNews(String typeSort, String begin) throws ServiceException;

    String deleteNews(String id) throws ServiceException;

    String createNews(Map<String, String> parameters) throws ServiceException;

    String changeImage(String id, String imageName) throws ServiceException;

    String changeNews(Map<String, String> parameters) throws ServiceException;
}
