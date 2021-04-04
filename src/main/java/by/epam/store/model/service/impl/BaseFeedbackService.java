package by.epam.store.model.service.impl;

import by.epam.store.model.dao.FeedbackDao;
import by.epam.store.model.dao.impl.BaseFeedbackDao;
import by.epam.store.model.entity.Feedback;
import by.epam.store.model.entity.User;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.FeedbackService;
import by.epam.store.annotation.Autowired;
import by.epam.store.annotation.Bean;
import by.epam.store.util.MessageKey;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.model.validator.NumberValidator;
import by.epam.store.model.validator.ValidationFields;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Base feedback service.
 */
@Bean
public class BaseFeedbackService implements FeedbackService {
    private final static Logger log = LogManager.getLogger(BaseFeedbackService.class);
    private FeedbackDao baseFeedbackDao;

    /**
     * Instantiates a new Base feedback service.
     */
    public BaseFeedbackService() { }

    /**
     * Instantiates a new Base feedback service.
     *
     * @param baseFeedbackDao the base feedback dao
     */
    public BaseFeedbackService(BaseFeedbackDao baseFeedbackDao) {
        this.baseFeedbackDao = baseFeedbackDao;
    }

    @Autowired
    public void setBaseFeedbackDao(FeedbackDao baseFeedbackDao) {
        this.baseFeedbackDao = baseFeedbackDao;
    }

    @Override
    public List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException {
        if (!NumberValidator.isLongValid(idProduct)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            long id = Long.parseLong(idProduct);
            return baseFeedbackDao.findAllByProductId(id);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
    }

    @Override
    public Optional<String> createFeedback(Map<String, String[]> parameters, User user) throws ServiceException {
        if (!ValidationFields.isMapParametersValid(parameters)||
                !parameters.containsKey(RequestParameterAndAttribute.FEEDBACK)||
                !parameters.containsKey(RequestParameterAndAttribute.EVALUATION)||
                !parameters.containsKey(RequestParameterAndAttribute.ID_PRODUCT)) {
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            String info = parameters.get(RequestParameterAndAttribute.FEEDBACK)[0];
            byte evaluation = Byte.parseByte(parameters.get(RequestParameterAndAttribute.EVALUATION)[0]);
            long idProduct = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_PRODUCT)[0]);
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
        if (!NumberValidator.isLongValid(id)) {
            return MessageKey.ERROR_UNKNOWN_FEEDBACK;
        }
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
