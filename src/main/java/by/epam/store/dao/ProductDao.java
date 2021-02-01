package by.epam.store.dao;

import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;

public interface ProductDao {
    public boolean changeStatus(Long id, TypeStatus status) throws DaoException;
}
