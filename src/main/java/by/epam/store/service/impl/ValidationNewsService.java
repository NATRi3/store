package by.epam.store.service.impl;

import by.epam.store.entity.News;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.NewsService;
import by.epam.store.util.MessageKey;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;

import java.util.List;
import java.util.Map;

public class ValidationNewsService implements NewsService {

    private final BaseNewsService service;

    public ValidationNewsService(BaseNewsService service) {
        this.service = service;
    }

    @Override
    public List<News> findFreshNews(String count) throws ServiceException {
        if (!NumberValidator.isLongValid(count)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        return service.findFreshNews(count);
    }

    @Override
    public List<News> findSortNews(String typeSort, String begin) throws ServiceException {
        if (!NumberValidator.isLongValid(begin) ||
                !TypeValidator.isTypeNewsSort(typeSort)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        return service.findSortNews(typeSort, begin);
    }

    @Override
    public String deleteNews(String id) throws ServiceException {
        if (!NumberValidator.isLongValid(id)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        return service.deleteNews(id);
    }

    @Override
    public String createNews(Map<String, String> parameters) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return service.createNews(parameters);
        } else {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
        if (NumberValidator.isLongValid(id)) {
            return service.changeImage(id, imageName);
        } else {
            return MessageKey.ERROR_UNKNOWN_NEWS;
        }
    }

    @Override
    public String changeNews(Map<String, String> parameters) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return service.changeNews(parameters);
        } else {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
    }
}
