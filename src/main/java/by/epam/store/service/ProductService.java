package by.epam.store.service;


import by.epam.store.dao.impl.ProductDao;
import by.epam.store.entity.Product;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService extends ImageService {
    static final ProductDao productDao = DaoCreator.getInstance().getProductDao();

    Optional<Product> findProductById(String id) throws ServiceException;

    String saveProduct(Map<String, String> parameters) throws ServiceException;

    String changeStatus(String id, TypeStatus status) throws ServiceException;

    List<Product> findProductByCollectionAndSort(String idCollection, String typeSort, String status, String begin) throws ServiceException;

    List<Product> findRandomProduct(String amount) throws ServiceException;

    String changeProduct(Map<String, String> parameters) throws ServiceException;

    String changeImage(String id, String imageName) throws ServiceException;

    List<Product> searchProduct(String name) throws ServiceException;
}
