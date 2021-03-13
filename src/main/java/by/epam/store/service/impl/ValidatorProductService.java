package by.epam.store.service.impl;

import by.epam.store.entity.Product;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.util.MessageKey;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ValidatorProductService implements ProductService {
    private static final Logger log = LogManager.getLogger(ValidatorProductService.class);

    private final BaseProductService productService;

    public ValidatorProductService(BaseProductService productService) {
        this.productService = productService;
    }

    @Override
    public Optional<Product> findProductById(String id) throws ServiceException {
        if (NumberValidator.isLongValid(id)) {
            return productService.findProductById(id);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public String saveProduct(Map<String, String> parameters) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return productService.saveProduct(parameters);
        } else {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
    }

    @Override
    public String changeStatus(String id, TypeStatus status) throws ServiceException {
        if (NumberValidator.isLongValid(id)) {
            return productService.changeStatus(id, status);
        } else {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
    }

    @Override
    public List<Product> findProductByCollectionAndSort(String idCollection, String typeSort, String status, String begin) throws ServiceException {
        return null;
    }

    @Override
    public List<Product> findRandomProduct(String amount) throws ServiceException {
        return null;
    }

    @Override
    public String changeProduct(Map<String, String> parameters) throws ServiceException {
        return null;
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
        return null;
    }

    @Override
    public List<Product> searchProduct(String name) throws ServiceException {
        return null;
    }
}
