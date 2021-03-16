package by.epam.store.service;

import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface CollectionService {

    List<ProductCollection> findAllProductCollectionsByStatus(String status) throws ServiceException;

    List<ProductCollection> findAllProductCollections() throws ServiceException;

    String createCollection(Map<String, String> parameters) throws ServiceException;

    String changeStatus(String id, TypeStatus status) throws ServiceException;

    String changeInfo(Map<String, String> parameters) throws ServiceException;
}
