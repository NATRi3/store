package by.epam.store.service;

import by.epam.store.dao.impl.CollectionDao;
import by.epam.store.entity.ProductCollection;
import by.epam.store.exception.ServiceException;

import java.util.List;

public interface CollectionService {
    static final CollectionDao collectionDao = DaoCreator.getInstance().getCollectionDao();

    public List<ProductCollection> getAllProductCollections() throws ServiceException;
}
