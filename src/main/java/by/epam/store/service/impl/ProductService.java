package by.epam.store.service.impl;

import by.epam.store.dao.impl.ProductDao;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductService implements by.epam.store.service.ProductService {
    public static final Logger logger = LogManager.getLogger(ProductService.class);
    private ProductDao productDao = new ProductDao();

    @Override
    public Product findProductById(long id) throws ServiceException {
        try {
            Optional<Product> optionalProduct = productDao.findEntityById(id);
            if(optionalProduct.isPresent()){
                return optionalProduct.get();
            } else{
                throw new ServiceException("Product not found");
            }
        } catch (DaoException e) {
            logger.error(e);
           throw new ServiceException(e);
        }
    }

    @Override
    public void saveProduct(Product product) throws ServiceException {
        try {
            if(product.getPrice().compareTo(BigDecimal.ZERO)>0) {
                productDao.create(product);
            }
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void deactive(long id) throws ServiceException{
        try {
            Optional<Product> optionalProduct = productDao.findEntityById(id);
            if(optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                product.setStatus(TypeStatus.NONACTIVE);
                productDao.update(product);
            }

        } catch (DaoException e){
            logger.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findProductByGenderAndType(String gender, String typeCloth) {
        List<Product> resultProducts = new ArrayList<>();
        resultProducts.add(new Product(1,"PRODUCT","THIS IS PRODUCT", BigDecimal.valueOf(10000), TypeStatus.ACTIVE));
        return resultProducts;
    }
}
