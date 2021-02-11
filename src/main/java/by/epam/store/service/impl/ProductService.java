package by.epam.store.service.impl;

import by.epam.store.entity.News;
import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.exception.UtilException;
import by.epam.store.service.BaseService;
import by.epam.store.util.FileNameGenerator;
import by.epam.store.util.FileUtil;
import by.epam.store.util.MessageErrorKey;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileItem;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Log4j2
public class ProductService implements by.epam.store.service.ProductService, BaseService {

    @Override
    public Product findProductById(String idProduct) throws ServiceException {
        try {
            if(!NumberValidator.isNumberValid(idProduct)){
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            long id = Long.parseLong(idProduct);
            Optional<Product> optionalProduct = productDao.findEntityById(id);
            if(optionalProduct.isPresent()){
                return optionalProduct.get();
            } else{
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_PRODUCT);
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void saveProduct(String name, String info, String price, String idCollection) throws ServiceException {
        try {
            if(!NumberValidator.isNumberValid(idCollection)){
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_COLLECTION);
            }
            if(!NumberValidator.isNumberValidForBigDecimal(price)){
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            long idCollect = Long.parseLong(idCollection);
            BigDecimal priceProduct = BigDecimal.valueOf(Double.parseDouble(price));
            Product product = new Product(name,info,priceProduct,idCollect);
            productDao.create(product);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public void changeStatus(String idProduct, TypeStatus status) throws ServiceException{
        try {
            if(!NumberValidator.isNumberValid(idProduct)){
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            long id = Long.parseLong(idProduct);
            Optional<Product> optionalProduct = productDao.findEntityById(id);
            if(optionalProduct.isPresent()){
                Product product = optionalProduct.get();
                product.setStatus(status);
                productDao.update(product);
            } else {
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_PRODUCT);
            }
        } catch (DaoException e){
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Product> findProductByCollectionAndSort(String idCollection, String typeSort, String status, String begin)
            throws ServiceException {
        try {
            int beginPagination;
            if(!TypeValidator.isTypeProductSort(typeSort)||
                !NumberValidator.isNumberValid(begin)||
                !TypeValidator.isTypeStatus(status)){
                log.error("Invalid param " + begin + "-" + typeSort + "-" + status);
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            if(!NumberValidator.isNumberValid(idCollection)){
                log.info("unknown collection "+ idCollection);
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_COLLECTION);
            }
            if(Long.parseLong(idCollection)==0){
                idCollection="%";
            }
            beginPagination = Integer.parseInt(begin);
            TypeStatus typeStatus = TypeStatus.valueOf(status);
            return productDao.findCollectionProductAndSort(beginPagination, typeStatus, idCollection, typeSort);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public List<Product> findRandomProduct(String amountStr) throws ServiceException {
        try {
            if(!NumberValidator.isNumberValid(amountStr)){
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            int amount = Integer.parseInt(amountStr);
            return productDao.findRandomProduct(amount);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public void changeProduct(String id, String info, String price) throws ServiceException {
        try {
            if(!NumberValidator.isNumberValid(id)){
                throw new ServiceException(MessageErrorKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            long idProduct = Long.parseLong(id);
            BigDecimal priceProduct = BigDecimal.valueOf(Long.parseLong(price));
            Optional<Product> optionalProduct = productDao.findEntityById(idProduct);
            if(optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setInfo(info);
                product.setPrice(priceProduct);
                productDao.update(product);
                return;
            }
            throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_PRODUCT);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public String changeProductImage(String id, List<FileItem> fileItems) throws ServiceException {
        if(!NumberValidator.isNumberValid(id)){
            throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_PRODUCT);
        }
        try {
            Optional<Product> optionalProduct = productDao.findEntityById(Long.valueOf(id));
            if(!optionalProduct.isPresent()){
                throw new ServiceException(MessageErrorKey.ERROR_UNKNOWN_PRODUCT);
            }
            Product product = optionalProduct.get();
            String newFileName = FileUtil.saveFile(fileItems);
            product.setImageName(newFileName);
            productDao.update(product);
            return newFileName;
        } catch (DaoException | UtilException e){
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }
}
