package by.epam.store.service.impl;

import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.CollectionService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameterAndAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseProductCollectionService implements CollectionService {
    private final static Logger log = LogManager.getLogger(BaseProductCollectionService.class);

    @Override
    public List<ProductCollection> findAllProductCollectionsByStatus(String status) throws ServiceException {
        try {
            TypeStatus typeStatus = TypeStatus.valueOf(status);
            return collectionDao.findByStatus(typeStatus);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductCollection> findAllProductCollections() throws ServiceException {
        try {
            return collectionDao.findAll();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<String> createCollection(Map<String, String> parameters) throws ServiceException {
        try {
            String name = parameters.get(RequestParameterAndAttribute.NAME_COLLECTION);
            String info = parameters.get(RequestParameterAndAttribute.INFO_COLLECTION);
            Date date = new Date();
            ProductCollection productCollection = new ProductCollection(name, info, date);
            collectionDao.create(productCollection);
            return Optional.of(MessageKey.SUCCESSFUL_CREATE_COLLECTION);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeStatus(String id, TypeStatus status) throws ServiceException {
        try {
            long idCollection = Long.parseLong(id);
            if (collectionDao.updateStatus(idCollection, status)) {
                return MessageKey.SUCCESSFUL_CHANGE;
            } else {
                return MessageKey.ERROR_UNKNOWN_COLLECTION;
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeInfo(Map<String, String> parameters) throws ServiceException {
        try {
            String messageKey;
            Long id = Long.valueOf(parameters.get(RequestParameterAndAttribute.ID_COLLECTION));
            String info = parameters.get(RequestParameterAndAttribute.INFO_COLLECTION);
            collectionDao.updateInfo(id, info);
            messageKey = MessageKey.SUCCESSFUL_CHANGE;
            return messageKey;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

}
