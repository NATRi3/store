package by.epam.store.service.impl;

import by.epam.store.entity.Product;
import by.epam.store.entity.type.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.util.FileUtil;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameter;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.fileupload.FileItem;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
public class ProductService implements by.epam.store.service.ProductService {

    @Override
    public Optional<Product> findProductById(String idProduct) throws ServiceException {
        try {
            if(NumberValidator.isNumberValid(idProduct)) {
                long id = Long.parseLong(idProduct);
                return productDao.findEntityById(id);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Optional<String> saveProduct(Map<String, String> parameters) throws ServiceException {
        try {
            if(FormValidator.isFormValid(parameters)) {
                long idCollection = Long.parseLong(parameters.get(RequestParameter.ID_COLLECTION));
                BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parameters.get(RequestParameter.PRICE_PRODUCT)));
                String name = parameters.get(RequestParameter.NAME_PRODUCT);
                String info = parameters.get(RequestParameter.INFO_PRODUCT);
                Product product = new Product(name, info, price, idCollection);
                productDao.create(product);
                return Optional.of(MessageKey.SUCCESSFUL_PRODUCT_ADD);
            } else {
                return Optional.of(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public String changeStatus(String idProduct, TypeStatus status) throws ServiceException{
        try {
            if(NumberValidator.isNumberValid(idProduct)) {
                long id = Long.parseLong(idProduct);
                Optional<Product> optionalProduct = productDao.findEntityById(id);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setStatus(status);
                    productDao.update(product);
                    return MessageKey.SUCCESSFUL_CHANGE;
                } else {
                    return MessageKey.ERROR_UNKNOWN_PRODUCT;
                }
            } else {
                return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
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
                throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            if(!NumberValidator.isNumberValid(idCollection)){
                log.info("unknown collection "+ idCollection);
                throw new ServiceException(MessageKey.ERROR_UNKNOWN_COLLECTION);
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
                throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            int amount = Integer.parseInt(amountStr);
            return productDao.findRandomProduct(amount);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
    }

    @Override
    public String changeProduct(Map<String, String> parameters) throws ServiceException {
        String resultMessage;
        try {
            if(FormValidator.isFormValid(parameters)) {
                long idProduct = Long.parseLong(parameters.get(RequestParameter.ID_PRODUCT));
                BigDecimal priceProduct = BigDecimal.valueOf(Double.parseDouble(parameters.get(RequestParameter.PRICE_PRODUCT)));
                String info = parameters.get(parameters.get(RequestParameter.INFO_PRODUCT));
                Optional<Product> optionalProduct = productDao.findEntityById(idProduct);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setInfo(info);
                    product.setPrice(priceProduct);
                    productDao.update(product);
                    resultMessage = MessageKey.SUCCESSFUL_CHANGE_PRODUCT;
                    parameters.clear();
                } else {
                    resultMessage = MessageKey.ERROR_UNKNOWN_PRODUCT;
                }
            }else {
                resultMessage = MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
        return resultMessage;
    }

    @Override
    public String changeProductImage(String id, List<FileItem> fileItems) throws ServiceException {
        String resultMessage = MessageKey.ERROR_MESSAGE_WRONG_FILE_TYPE;
        try {
            if(NumberValidator.isNumberValid(id)) {
                Optional<Product> optionalProduct = productDao.findEntityById(Long.valueOf(id));
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    for(FileItem fileItem: fileItems) {
                        Optional<String> optional = FileUtil.saveFile(fileItem);
                        if(optional.isPresent()) {
                            String newFileName = optional.get();
                            product.setImageName(newFileName);
                            if (productDao.update(product)) {
                                resultMessage = MessageKey.SUCCESSFUL_CHANGE_IMAGE;
                            } else {
                                resultMessage = MessageKey.ERROR_UNKNOWN_PRODUCT;
                            }
                        }
                    }
                } else {
                    resultMessage = MessageKey.ERROR_UNKNOWN_PRODUCT;
                }
            } else {
                resultMessage = MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
        } catch (Exception e){
            log.error(e);
            throw new ServiceException(e.getMessage(),e);
        }
        return resultMessage;
    }

    @Override
    public List<Product> findProductByName(String name) throws ServiceException {
        try{
            String searchName = '%'+name+'%';
            return productDao.findProductByName(name);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException();
        }
    }
}
