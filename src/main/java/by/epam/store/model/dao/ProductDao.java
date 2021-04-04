package by.epam.store.model.dao;

import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.annotation.Dependency;

import java.util.List;

/**
 * The interface Product dao.
 */
@Dependency
public interface ProductDao extends BaseDao<Product> {
    /**
     * Change status boolean.
     *
     * @param id     the id
     * @param status the status
     * @return the boolean
     * @throws DaoException the dao exception
     */
    boolean changeStatus(Long id, TypeStatus status) throws DaoException;

    /**
     * Find collection product and sort list.
     *
     * @param begin        the begin
     * @param status       the status
     * @param idCollection the id collection
     * @param typeSort     the type sort
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Product> findCollectionProductAndSort(int begin, TypeStatus status, String idCollection, String typeSort)
            throws DaoException;

    /**
     * Find random product list.
     *
     * @param amount the amount
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Product> findRandomProduct(int amount) throws DaoException;

    /**
     * Find product by name list.
     *
     * @param name the name
     * @return the list
     * @throws DaoException the dao exception
     */
    List<Product> findProductByName(String... name) throws DaoException;
}
