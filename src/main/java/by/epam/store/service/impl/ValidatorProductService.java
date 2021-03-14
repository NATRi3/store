package by.epam.store.service.impl;

import by.epam.store.entity.Product;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.util.MessageKey;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
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
    public List<Product> findProductByCollectionAndSort(String idCollection, String typeSort, String status, String begin)
            throws ServiceException {
        if (!TypeValidator.isTypeProductSort(typeSort) ||
                !NumberValidator.isLongValid(begin) ||
                !TypeValidator.isTypeStatus(status)) {
            log.error("Invalid param " + begin + "-" + typeSort + "-" + status);
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        if (!NumberValidator.isLongValid(idCollection)) {
            log.info("unknown collection " + idCollection);
            throw new ServiceException(MessageKey.ERROR_UNKNOWN_COLLECTION);
        }
        if (Long.parseLong(idCollection) == 0) {
            idCollection = "%";
        }
        return productService.findProductByCollectionAndSort(idCollection, typeSort, status, begin);
    }

    @Override
    public List<Product> findRandomProduct(String amountStr) throws ServiceException {
        if (!NumberValidator.isLongValid(amountStr)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        return productService.findRandomProduct(amountStr);
    }

    @Override
    public String changeProduct(Map<String, String> parameters) throws ServiceException {
        if (FormValidator.isFormValid(parameters)) {
            return productService.changeProduct(parameters);
        } else {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
            if (NumberValidator.isLongValid(id)) {
                return productService.changeImage(id,imageName);
            } else {
                return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
    }

    @Override
    public List<Product> searchProduct(String searchRequest) throws ServiceException {
        return productService.searchProduct(searchRequest);
    }
}
