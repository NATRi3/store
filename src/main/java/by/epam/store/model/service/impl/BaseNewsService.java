package by.epam.store.model.service.impl;

import by.epam.store.model.dao.NewsDao;
import by.epam.store.model.entity.News;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.NewsService;
import by.epam.store.annotation.Autowired;
import by.epam.store.annotation.Bean;
import by.epam.store.util.MessageKey;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.model.validator.NumberValidator;
import by.epam.store.model.validator.TypeValidator;
import by.epam.store.model.validator.ValidationFields;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type Base news service.
 */
@Bean
public class BaseNewsService implements NewsService {
    private static final Logger log = LogManager.getLogger(BaseNewsService.class);
    private NewsDao newsDao;



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

    }

    @Override
    public List<News> findFreshNews(String count) throws ServiceException {
        if (!NumberValidator.isLongValid(count)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            return newsDao.findFreshNews(Integer.parseInt(count));
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<News> findSortNews(String typeSort, String begin) throws ServiceException {
        if (!NumberValidator.isLongValid(begin) ||
                !TypeValidator.isTypeNewsSort(typeSort)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            return newsDao.findSortNews(typeSort, Integer.parseInt(begin), 10);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public String deleteNews(String id) throws ServiceException {
        if (!NumberValidator.isLongValid(id)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
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
    public String createNews(Map<String, String[]> parameters) throws ServiceException {
        if (!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.NEWS_TITLE) ||
                !parameters.containsKey(RequestParameterAndAttribute.NEWS_INFO)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        try {
            Date date = new Date();
            String title = parameters.get(RequestParameterAndAttribute.NEWS_TITLE)[0];
            String info = parameters.get(RequestParameterAndAttribute.NEWS_INFO)[0];
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
        if (NumberValidator.isLongValid(id)) {
            try {
                if (newsDao.changeImage(Long.parseLong(id), imageName)) {
                    resultMessage = MessageKey.SUCCESSFUL_CHANGE_IMAGE;
                } else {
                    resultMessage = MessageKey.ERROR_UNKNOWN_NEWS;
                }
            } catch (Exception e) {
                log.error(e);
                throw new ServiceException(e.getMessage(), e);
            }
        } else {
            resultMessage =  MessageKey.ERROR_UNKNOWN_NEWS;
        }
        return resultMessage;
    }

    @Override
    public String changeNews(Map<String, String[]> parameters) throws ServiceException {
        if (ValidationFields.isMapParametersValid(parameters) &&
                parameters.containsKey(RequestParameterAndAttribute.NEWS_TITLE) &&
                parameters.containsKey(RequestParameterAndAttribute.NEWS_INFO)&&
                parameters.containsKey(RequestParameterAndAttribute.ID_NEWS)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        try {
            String resultMessage;
            long id = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_NEWS)[0]);
            String title = parameters.get(RequestParameterAndAttribute.NEWS_TITLE)[0];
            String info = parameters.get(RequestParameterAndAttribute.NEWS_INFO)[0];
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
    @Autowired
    public void setNewsDao(NewsDao newsDao) {
        this.newsDao = newsDao;
    }
}
