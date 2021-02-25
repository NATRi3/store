package by.epam.store.service;

import by.epam.store.dao.impl.CollectionDao;
import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CollectionService {
    static final CollectionDao collectionDao = DaoCreator.getInstance().getCollectionDao();

    public List<ProductCollection> findAllProductCollectionsByStatus(String status) throws ServiceException;

    public List<ProductCollection> findAllProductCollections() throws ServiceException;

    Optional<String> createCollection(Map<String, String> parameters) throws ServiceException;

    String changeStatus(String id, TypeStatus status) throws ServiceException;

    String changeInfo(Map<String,String> parameters) throws ServiceException;
}
