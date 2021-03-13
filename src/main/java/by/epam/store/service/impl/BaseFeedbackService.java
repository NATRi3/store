package by.epam.store.service.impl;

import by.epam.store.entity.Feedback;
import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.FeedbackService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseFeedbackService implements FeedbackService {
    private final static Logger log = LogManager.getLogger(BaseFeedbackService.class);

    @Override
    public List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException {
        try {
            long id = Long.parseLong(idProduct);
            return feedbackDao.findAllByProductId(id);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<String> createFeedback(Map<String, String> parameters, User user) throws ServiceException {
        try {
            String info = parameters.get(RequestParameterAndAttribute.FEEDBACK);
            byte evaluation = Byte.parseByte(parameters.get(RequestParameterAndAttribute.EVALUATION));
            long idProduct = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_PRODUCT));
            Date date = new Date();
            Feedback feedback = new Feedback(info, evaluation, idProduct, user, date);
            feedbackDao.create(feedback);
            return Optional.empty();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String deleteFeedback(String id) throws ServiceException {
        try {
            long idFeedback = Long.parseLong(id);
            if (feedbackDao.delete(idFeedback)) {
                return MessageKey.SUCCESSFUL_DELETE;
            } else {
                return MessageKey.ERROR_UNKNOWN_FEEDBACK;
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }

    }
}
