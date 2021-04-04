package by.epam.store.model.service.impl;

import by.epam.store.model.dao.CollectionDao;
import by.epam.store.model.dao.impl.BaseCollectionDao;
import by.epam.store.model.entity.ProductCollection;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.CollectionService;
import by.epam.store.annotation.Autowired;
import by.epam.store.annotation.Bean;
import by.epam.store.util.MessageKey;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.model.validator.NumberValidator;
import by.epam.store.model.validator.TypeValidator;
import by.epam.store.model.validator.ValidationFields;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * The type Base product collection service.
 */
@Bean
public class BaseProductCollectionService implements CollectionService {
    private final static Logger log = LogManager.getLogger(BaseProductCollectionService.class);
    private CollectionDao baseCollectionDao;

    /**
     * Instantiates a new Base product collection service.
     */
    public BaseProductCollectionService() {

    }

    /**
     * Instantiates a new Base product collection service.
     *
     * @param baseCollectionDao the base collection dao
     */
    public BaseProductCollectionService(BaseCollectionDao baseCollectionDao) {
        this.baseCollectionDao = baseCollectionDao;
    }

    @Override
    public List<ProductCollection> findAllProductCollectionsByStatus(String status) throws ServiceException {
        if (!TypeValidator.isTypeCollectionStatus(status)) {
            throw new ServiceException("Invalid parameter " + status);
        }
        try {
            TypeStatus typeStatus = TypeStatus.valueOf(status);
            return baseCollectionDao.findByStatus(typeStatus);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<ProductCollection> findAllProductCollections() throws ServiceException {
        try {
            return baseCollectionDao.findAll();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String createCollection(Map<String, String[]> parameters) throws ServiceException {
        if (!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.NAME_COLLECTION) ||
                !parameters.containsKey(RequestParameterAndAttribute.INFO_COLLECTION)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        try {
            String name = parameters.get(RequestParameterAndAttribute.NAME_COLLECTION)[0];
            String info = parameters.get(RequestParameterAndAttribute.INFO_COLLECTION)[0];
            ProductCollection productCollection = ProductCollection.builder()
                    .date(new Date())
                    .info(info)
                    .name(name)
                    .build();
            baseCollectionDao.create(productCollection);
            return MessageKey.SUCCESSFUL_CREATE_COLLECTION;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeStatus(String id, TypeStatus status) throws ServiceException {
        if (!NumberValidator.isLongValid(id)) {
            throw new ServiceException("Invalid id " + id);
        }
        try {
            long idCollection = Long.parseLong(id);
            if (baseCollectionDao.updateStatus(idCollection, status)) {
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
    public String changeInfo(Map<String, String[]> parameters) throws ServiceException {
        if (!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.ID_COLLECTION) ||
                !parameters.containsKey(RequestParameterAndAttribute.INFO_COLLECTION)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        try {
            String messageKey;
            Long id = Long.valueOf(parameters.get(RequestParameterAndAttribute.ID_COLLECTION)[0]);
            String info = parameters.get(RequestParameterAndAttribute.INFO_COLLECTION)[0];
            baseCollectionDao.updateInfo(id, info);
            messageKey = MessageKey.SUCCESSFUL_CHANGE;
            return messageKey;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Autowired
    public void setBaseCollectionDao(CollectionDao baseCollectionDao) {
        this.baseCollectionDao = baseCollectionDao;
    }
}
