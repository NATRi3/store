package by.epam.store.service.impl;

import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.CollectionService;
import by.epam.store.util.MessageKey;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ValidationProductCollectionService implements CollectionService {

    private final BaseProductCollectionService service;

    public ValidationProductCollectionService(BaseProductCollectionService service) {
        this.service = service;
    }

    @Override
    public List<ProductCollection> findAllProductCollectionsByStatus(String status) throws ServiceException {
        if (!TypeValidator.isTypeCollectionStatus(status)) {
            throw new ServiceException();
        }
        return service.findAllProductCollectionsByStatus(status);
    }

    @Override
    public List<ProductCollection> findAllProductCollections() throws ServiceException {
        return service.findAllProductCollections();
    }

    @Override
    public String createCollection(Map<String, String> parameters) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return service.createCollection(parameters);
        } else {
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
    }

    @Override
    public String changeStatus(String id, TypeStatus status) throws ServiceException {
        if (!NumberValidator.isLongValid(id)) {
            throw new ServiceException("Invalid id " + id);
        }
        return service.changeStatus(id, status);
    }

    @Override
    public String changeInfo(Map<String, String> parameters) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return service.changeInfo(parameters);
        } else {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
    }
}
