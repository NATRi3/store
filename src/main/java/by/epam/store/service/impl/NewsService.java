package by.epam.store.service.impl;

import by.epam.store.entity.News;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.exception.UtilException;
import by.epam.store.service.BaseService;
import by.epam.store.util.FileUtil;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileItem;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Log4j2
public class NewsService implements BaseService, by.epam.store.service.NewsService {

    @Override
    public List<News> getFreshNews(String count) throws ServiceException {
        if(!NumberValidator.isNumberValid(count)){
            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            return newsDao.getFreshNews(Integer.parseInt(count));
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<News> getSortNews(String typeSort, String begin) throws ServiceException {
        if(!NumberValidator.isNumberValid(begin)||
            !TypeValidator.isTypeNewsSort(typeSort)){

            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try{
            return newsDao.getSortNews(typeSort, Integer.parseInt(begin),10);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public void deleteNews(String id) throws ServiceException {
        if(!NumberValidator.isNumberValid(id)){
            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try{
            if(!newsDao.delete(Long.valueOf(id))){
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_NEWS);
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public void createNews(String title, String info) throws ServiceException {
        try{
            Date date = new Date();
            News news = new News(title,info,date);
            newsDao.create(news);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public void changeImage(String id, List<FileItem> fileItems) throws ServiceException {
        if(!NumberValidator.isNumberValid(id)){
            throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_NEWS);
        }
        try {
            Optional<News> optionalNews = newsDao.findEntityById(Long.valueOf(id));
            if (!optionalNews.isPresent()) {
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_NEWS);
            }
            News news = optionalNews.get();
            String nameNewFile = FileUtil.saveFile(fileItems);
            news.setImageName(nameNewFile);
            if(!newsDao.update(news)){
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_NEWS);
            }
        } catch (DaoException | UtilException e){
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public void changeNews(String id, String title, String info) throws ServiceException {
        if(!NumberValidator.isNumberValid(id)){
            throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_NEWS);
        }
        try {
            Optional<News> optionalNews = newsDao.findEntityById(Long.valueOf(id));
            if (!optionalNews.isPresent()) {
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_NEWS);
            }
            News news = optionalNews.get();
            news.setTitle(title);
            news.setInfo(info);
            if(!newsDao.update(news)){
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_NEWS);
            }
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }
}
