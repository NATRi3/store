package by.epam.store.service.impl;

import by.epam.store.entity.Feedback;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.FeedbackService;
import by.epam.store.util.MessageKey;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Validation feedback service.
 */
public class ValidationFeedbackService implements FeedbackService {

    private final BaseFeedbackService service;

    /**
     * Instantiates a new Validation feedback service.
     *
     * @param service the service
     */
    public ValidationFeedbackService(BaseFeedbackService service) {
        this.service = service;
    }

    @Override
    public List<Feedback> getFeedbackByIdProduct(String idProduct) throws ServiceException {
        if (!NumberValidator.isLongValid(idProduct)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        return service.getFeedbackByIdProduct(idProduct);
    }

    @Override
    public Optional<String> createFeedback(Map<String, String> parameters, User user) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return service.createFeedback(parameters, user);
        } else {
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
    }

    @Override
    public String deleteFeedback(String id) throws ServiceException {
        if (NumberValidator.isLongValid(id)) {
            return service.deleteFeedback(id);
        } else {
            return MessageKey.ERROR_UNKNOWN_FEEDBACK;
        }
    }
}
