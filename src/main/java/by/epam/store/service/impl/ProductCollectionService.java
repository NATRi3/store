package by.epam.store.service.impl;

import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductCollectionService implements by.epam.store.service.CollectionService {
    private final static Logger log = LogManager.getLogger(ProductCollectionService.class);

    @Override
    public List<ProductCollection> findAllProductCollectionsByStatus(String status) throws ServiceException {
        try {
            if(!TypeValidator.isTypeCollectionStatus(status)){
                throw new ServiceException();
            }
            TypeStatus typeStatus = TypeStatus.valueOf(status);
            return collectionDao.findByStatus(typeStatus);
        } catch (DaoException e){
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
        try{
            if(FormValidator.isFormValid(parameters)){
                String name = parameters.get(RequestParameter.NAME_COLLECTION);
                String info = parameters.get(RequestParameter.INFO_COLLECTION);
                Date date = new Date();
                ProductCollection productCollection = new ProductCollection(name,info,date);
                collectionDao.create(productCollection);
                return Optional.of(MessageKey.SUCCESSFUL_CREATE_COLLECTION);
            }
            return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeStatus(String id, TypeStatus status) throws ServiceException {
        if(!NumberValidator.isLongValid(id)){
            throw new ServiceException("Invalid id "+id);
        }
        try{
            long idCollection = Long.parseLong(id);
            if(collectionDao.updateStatus(idCollection,status)){
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
    public String changeInfo(Map<String,String> parameters) throws ServiceException {
        try{
            String messageKey;
            if(FormValidator.isFormValid(parameters)){
                Long id = Long.valueOf(parameters.get(RequestParameter.ID_COLLECTION));
                String info = parameters.get(RequestParameter.INFO_COLLECTION);
                collectionDao.updateInfo(id,info);
                messageKey = MessageKey.SUCCESSFUL_CHANGE;
            } else {
                messageKey = MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
            return messageKey;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

}
