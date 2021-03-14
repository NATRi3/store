package by.epam.store.dao;

import by.epam.store.entity.Product;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;

import java.util.List;

public interface ProductDao extends BaseDao<Product> {
    boolean changeStatus(Long id, TypeStatus status) throws DaoException;

    List<Product> findCollectionProductAndSort(int begin, TypeStatus status, String idCollection, String typeSort)
            throws DaoException;

    List<Product> findRandomProduct(int amount) throws DaoException;

    List<Product> findProductByName(String... name) throws DaoException;
}
