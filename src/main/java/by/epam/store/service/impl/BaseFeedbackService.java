package by.epam.store.service.impl;

import by.epam.store.dao.DaoCreator;
import by.epam.store.dao.FeedbackDao;
import by.epam.store.dao.impl.BaseFeedbackDao;
import by.epam.store.entity.Feedback;
import by.epam.store.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.FeedbackService;
import by.epam.store.util.MessageKey;
import by.epam.store.command.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Base feedback service.
 */
public class BaseFeedbackService implements FeedbackService {
    private final static Logger log = LogManager.getLogger(BaseFeedbackService.class);
    private final FeedbackDao baseFeedbackDao;

    /**
     * Instantiates a new Base feedback service.
     */
    public BaseFeedbackService() {
        baseFeedbackDao = DaoCreator.getInstance().getFeedbackDao();
    }

    /**
     * Instantiates a new Base feedback service.
     *
     * @param baseFeedbackDao the base feedback dao
     */
    public BaseFeedbackService(BaseFeedbackDao baseFeedbackDao) {
        this.baseFeedbackDao = baseFeedbackDao;
    }

    @Override
    public List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException {
        try {
            long id = Long.parseLong(idProduct);
            return baseFeedbackDao.findAllByProductId(id);
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
            Feedback feedback = Feedback.builder()
                    .feedback(info)
                    .evaluation(evaluation)
                    .idProduct(idProduct)
                    .user(user)
                    .date(date)
                    .build();
            baseFeedbackDao.create(feedback);
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
            if (baseFeedbackDao.delete(idFeedback)) {
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
