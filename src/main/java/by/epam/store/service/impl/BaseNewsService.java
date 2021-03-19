package by.epam.store.service.impl;

import by.epam.store.dao.DaoCreator;
import by.epam.store.dao.NewsDao;
import by.epam.store.entity.News;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.NewsService;
import by.epam.store.util.MessageKey;
import by.epam.store.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type Base news service.
 */
public class BaseNewsService implements NewsService {
    private static final Logger log = LogManager.getLogger(BaseNewsService.class);
    private final NewsDao newsDao;

    /**
     * Instantiates a new Base news service.
     *
     * @param newsDao the news dao
     */
    public BaseNewsService(NewsDao newsDao) {
        this.newsDao = newsDao;
    }

    /**
     * Instantiates a new Base news service.
     */
    public BaseNewsService() {
        newsDao = DaoCreator.getInstance().getNewsDao();
    }

    @Override
    public List<News> findFreshNews(String count) throws ServiceException {
        try {
            return newsDao.findFreshNews(Integer.parseInt(count));
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<News> findSortNews(String typeSort, String begin) throws ServiceException {
        try {
            return newsDao.findSortNews(typeSort, Integer.parseInt(begin), 10);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String deleteNews(String id) throws ServiceException {
        try {
            if (newsDao.delete(Long.valueOf(id))) {
                return MessageKey.SUCCESSFUL_NEWS_DELETE;
            } else {
                return MessageKey.ERROR_UNKNOWN_NEWS;
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String createNews(Map<String, String> parameters) throws ServiceException {
        try {
            Date date = new Date();
            String title = parameters.get(RequestParameterAndAttribute.NEWS_TITLE);
            String info = parameters.get(RequestParameterAndAttribute.NEWS_INFO);
            News news = News.builder()
                    .date(date)
                    .title(title)
                    .info(info)
                    .build();
            newsDao.create(news);
            return MessageKey.SUCCESSFUL_NEWS_ADD;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
        String resultMessage;
        try {
            if (newsDao.changeImage(Long.parseLong(id), imageName)) {
                resultMessage = MessageKey.SUCCESSFUL_CHANGE_IMAGE;
            } else {
                resultMessage = MessageKey.ERROR_UNKNOWN_NEWS;
            }
            return resultMessage;
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String changeNews(Map<String, String> parameters) throws ServiceException {
        try {
            String resultMessage;
            long id = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_NEWS));
            String title = parameters.get(RequestParameterAndAttribute.NEWS_TITLE);
            String info = parameters.get(RequestParameterAndAttribute.NEWS_INFO);
            News news = News.builder()
                    .info(info)
                    .title(title)
                    .idNews(id)
                    .build();
            if (newsDao.update(news)) {
                resultMessage = MessageKey.SUCCESSFUL_CHANGE;
                parameters.clear();
            } else {
                resultMessage = MessageKey.ERROR_UNKNOWN_NEWS;
            }
            return resultMessage;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
    }
}
