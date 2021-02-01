package by.epam.store.service;


import by.epam.store.entity.Product;
import by.epam.store.exception.ServiceException;

import java.util.List;

public interface ProductService {
    Product findProductById(long id) throws ServiceException;
    void saveProduct(Product product) throws ServiceException;
    void deactive(long id) throws ServiceException;
    List<Product> findProductByGenderAndType(String gender, String typeCloth);
}
