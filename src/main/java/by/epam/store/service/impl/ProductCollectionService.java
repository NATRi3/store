package by.epam.store.service.impl;

import by.epam.store.entity.ProductCollection;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
public class ProductCollectionService implements by.epam.store.service.CollectionService {

    @Override
    public List<ProductCollection> getAllProductCollections() throws ServiceException {
        try {
            return collectionDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
