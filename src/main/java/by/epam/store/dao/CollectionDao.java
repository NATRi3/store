package by.epam.store.dao;

import by.epam.store.entity.ProductCollection;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;

import java.util.List;

public interface CollectionDao {
    List<ProductCollection> findByStatus(TypeStatus typeStatus) throws DaoException;

    boolean updateStatus(long idCollection, TypeStatus status) throws DaoException;

    boolean updateInfo(Long id, String info) throws DaoException;
}
