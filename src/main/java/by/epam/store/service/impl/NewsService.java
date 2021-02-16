package by.epam.store.service.impl;

import by.epam.store.entity.News;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.FileUtil;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import org.apache.commons.fileupload.FileItem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NewsService implements by.epam.store.service.NewsService {
    private static final Logger log = LogManager.getLogger(NewsService.class);
    @Override
    public List<News> getFreshNews(String count) throws ServiceException {
        if(!NumberValidator.isNumberValid(count)){
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
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
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try{
            return newsDao.getSortNews(typeSort, Integer.parseInt(begin),10);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public String deleteNews(String id) throws ServiceException {
        if(!NumberValidator.isNumberValid(id)){
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        try{
            if(newsDao.delete(Long.valueOf(id))){
                return MessageKey.SUCCESSFUL_NEWS_DELETE;
            }else {
                return MessageKey.ERROR_UNKNOWN_NEWS;
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public Optional<String> createNews(Map<String, String> parameters) throws ServiceException {
        try{
            if(FormValidator.isFormValid(parameters)) {
                Date date = new Date();
                String title = parameters.get(RequestParameter.NEWS_TITLE);
                String info = parameters.get(RequestParameter.NEWS_INFO);
                News news = new News(title, info, date);
                newsDao.create(news);
                return Optional.of(MessageKey.SUCCESSFUL_NEWS_ADD);
            } else {
                return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public String changeImage(String id, List<FileItem> fileItems, String realPath) throws ServiceException {
        String resultMessage = MessageKey.ERROR_MESSAGE_FILE_NOT_FOUND;
        try {
            if(NumberValidator.isNumberValid(id)) {
                Optional<News> optionalNews = newsDao.findEntityById(Long.valueOf(id));
                if (optionalNews.isPresent()) {
                    News news = optionalNews.get();
                    for(FileItem fileItem: fileItems) {
                        Optional<String> optional = FileUtil.saveFile(fileItem,realPath);
                        if(optional.isPresent()){
                            String nameFile = optional.get();
                            news.setImageName(nameFile);
                            if (newsDao.update(news)) {
                                resultMessage = MessageKey.SUCCESSFUL_CHANGE_IMAGE;
                            }else {
                                resultMessage = MessageKey.ERROR_UNKNOWN_NEWS;
                            }
                        } else {
                            resultMessage = MessageKey.ERROR_MESSAGE_WRONG_FILE_TYPE;
                        }
                    }
                }else {
                    resultMessage = MessageKey.ERROR_UNKNOWN_NEWS;
                }
            }else {
                resultMessage = MessageKey.ERROR_UNKNOWN_NEWS;
            }
            return resultMessage;
        } catch (Exception e){
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public String changeNews(Map<String, String> parameters) throws ServiceException {
        String resultMessage;
        try {
            if(FormValidator.isFormValid(parameters)) {
                long id = Long.parseLong(parameters.get(RequestParameter.ID_NEWS));
                String title = parameters.get(RequestParameter.NEWS_TITLE);
                String info = parameters.get(RequestParameter.NEWS_INFO);
                Optional<News> optionalNews = newsDao.findEntityById(id);
                if (optionalNews.isPresent()) {
                    News news = optionalNews.get();
                    news.setTitle(title);
                    news.setInfo(info);
                    if (newsDao.update(news)) {
                        resultMessage = MessageKey.SUCCESSFUL_CHANGE;
                        parameters.clear();
                    }else {
                        resultMessage = MessageKey.ERROR_UNKNOWN_NEWS;
                    }
                }else {
                    resultMessage = MessageKey.ERROR_UNKNOWN_NEWS;
                }
            }else {
                resultMessage = MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
        return resultMessage;
    }
}
