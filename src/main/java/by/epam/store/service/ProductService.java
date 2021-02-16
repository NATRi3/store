package by.epam.store.service;


import by.epam.store.dao.impl.ProductDao;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    static final ProductDao productDao = DaoCreator.getInstance().getProductDao();

    Optional<Product> findProductById(String id) throws ServiceException;
    Optional<String> saveProduct(Map<String, String> parameters) throws ServiceException;
    String changeStatus(String id, TypeStatus status) throws ServiceException;
    List<Product> findProductByCollectionAndSort(String idCollection, String typeSort, String status, String begin) throws ServiceException;
    List<Product> findRandomProduct(String amount) throws ServiceException;
    String changeProduct(Map<String, String> parameters) throws ServiceException;
    String changeProductImage(String id, List<FileItem> fileItems, String realPath) throws ServiceException;
    List<Product> findProductByName(String name) throws ServiceException;
}
