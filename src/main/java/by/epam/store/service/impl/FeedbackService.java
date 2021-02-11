package by.epam.store.service.impl;

import by.epam.store.entity.Feedback;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.BaseService;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.validator.NumberValidator;
import lombok.extern.log4j.Log4j2;

import java.util.List;
@Log4j2
public class FeedbackService implements by.epam.store.service.FeedbackService, BaseService {
    @Override
    public List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException {
        if(!NumberValidator.isNumberValid(idProduct)){
            throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            long id = Long.parseLong(idProduct);
            return feedbackDao.findAllByProductId(id);
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }
}
