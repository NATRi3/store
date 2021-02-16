package by.epam.store.service.impl;

import by.epam.store.entity.ProductCollection;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProductCollectionService implements by.epam.store.service.CollectionService {
    private final static Logger log = LogManager.getLogger(ProductCollectionService.class);
    @Override
    public List<ProductCollection> getAllProductCollections() throws ServiceException {
        try {
            return collectionDao.findAll();
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }
}
