package by.epam.store.dao;

import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.service.TypeSort;

import java.util.List;

public interface ProductDao {
    public boolean changeStatus(Long id, TypeStatus status) throws DaoException;
    public List<Product> findCollectionProductAndSort(int begin, TypeStatus status, String idCollection, String typeSort)
            throws DaoException;
    List<Product> findRandomProduct(int amount) throws DaoException;
}
