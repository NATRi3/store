package by.epam.store.service.impl;

import by.epam.store.entity.Feedback;
import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class FeedbackService implements by.epam.store.service.FeedbackService {
    @Override
    public List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException {
        if(!NumberValidator.isNumberValid(idProduct)){
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            long id = Long.parseLong(idProduct);
            return feedbackDao.findAllByProductId(id);
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public Optional<String> createFeedback(Map<String, String> parameters, User user) throws ServiceException {
        try {
            if (FormValidator.isFormValid(parameters)) {
                String info = parameters.get(RequestParameter.FEEDBACK);
                byte evaluation = Byte.parseByte(parameters.get(RequestParameter.EVALUATION));
                long idProduct = Long.parseLong(parameters.get(RequestParameter.ID_PRODUCT));
                Date date = new Date();
                Feedback feedback = new Feedback(info,evaluation,idProduct,user,date);
                feedbackDao.create(feedback);
                return Optional.empty();
            } else {
                return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
        }catch (DaoException e){
            log.error(e);
            throw new ServiceException(e);
        }
    }
}
