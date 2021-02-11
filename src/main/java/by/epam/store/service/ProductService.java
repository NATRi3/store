package by.epam.store.service;


import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.ServiceException;
import org.apache.commons.fileupload.FileItem;

import java.util.List;

public interface ProductService {
    Product findProductById(String id) throws ServiceException;
    void saveProduct(String name, String info, String price, String idCollection) throws ServiceException;
    void changeStatus(String id, TypeStatus status) throws ServiceException;
    List<Product> findProductByCollectionAndSort(String idCollection, String typeSort, String status, String begin) throws ServiceException;
    List<Product> findRandomProduct(String amount) throws ServiceException;
    void changeProduct(String id, String info, String price) throws ServiceException;
    String changeProductImage(String id, List<FileItem> fileItems) throws ServiceException;
}
