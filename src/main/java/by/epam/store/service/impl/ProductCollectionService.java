package by.epam.store.service.impl;

import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.validator.TypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

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

}
