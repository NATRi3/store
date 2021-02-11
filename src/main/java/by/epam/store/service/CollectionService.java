package by.epam.store.service;

import by.epam.store.entity.ProductCollection;
import by.epam.store.exception.ServiceException;

import java.util.List;

public interface CollectionService {
    public List<ProductCollection> getAllProductCollections() throws ServiceException;
}
