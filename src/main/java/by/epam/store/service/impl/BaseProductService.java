package by.epam.store.service.impl;

import by.epam.store.dao.DaoCreator;
import by.epam.store.dao.impl.ProductDao;
import by.epam.store.entity.Product;
import by.epam.store.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ProductService;
import by.epam.store.util.MessageKey;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.validator.FormValidator;
import by.epam.store.validator.NumberValidator;
import by.epam.store.validator.TypeValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BaseProductService implements ProductService {
    private static final Logger log = LogManager.getLogger(BaseProductService.class);
    private static final ProductDao productDao = DaoCreator.getInstance().getProductDao();

    @Override
    public Optional<Product> findProductById(String idProduct) throws ServiceException {
        try {
            long id = Long.parseLong(idProduct);
            return productDao.findEntityById(id);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String saveProduct(Map<String, String> parameters) throws ServiceException {
        try {
            long idCollection = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_COLLECTION));
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parameters.get(RequestParameterAndAttribute.PRICE_PRODUCT)));
            String name = parameters.get(RequestParameterAndAttribute.NAME_PRODUCT);
            String info = parameters.get(RequestParameterAndAttribute.INFO_PRODUCT);
            Product product = new Product(name, info, price, idCollection);
            productDao.create(product);
            return MessageKey.SUCCESSFUL_PRODUCT_ADD;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeStatus(String idProduct, TypeStatus status) throws ServiceException {
        String resultMessage;
        try {
            long id = Long.parseLong(idProduct);
            Optional<Product> optionalProduct = productDao.findEntityById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setStatus(status);
                productDao.update(product);
                resultMessage = MessageKey.SUCCESSFUL_CHANGE;
            } else {
                resultMessage = MessageKey.ERROR_UNKNOWN_PRODUCT;
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return resultMessage;
    }

    @Override
    public List<Product> findProductByCollectionAndSort(String idCollection, String typeSort, String status, String begin)
            throws ServiceException {
        try {
            int beginPagination = Integer.parseInt(begin);
            TypeStatus typeStatus = TypeStatus.valueOf(status);
            return productDao.findCollectionProductAndSort(beginPagination, typeStatus, idCollection, typeSort);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findRandomProduct(String amountStr) throws ServiceException {
        try {
            if (!NumberValidator.isLongValid(amountStr)) {
                throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            int amount = Integer.parseInt(amountStr);
            return productDao.findRandomProduct(amount);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeProduct(Map<String, String> parameters) throws ServiceException {
        String resultMessage;
        try {
            if (FormValidator.isFormValid(parameters)) {
                long idProduct = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_PRODUCT));
                BigDecimal priceProduct = BigDecimal.valueOf(Double.parseDouble(parameters.get(RequestParameterAndAttribute.PRICE_PRODUCT)));
                String info = parameters.get(RequestParameterAndAttribute.INFO_PRODUCT);
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
            } else {
                resultMessage = MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
        return resultMessage;
    }

    @Override
    public String changeImage(String id, String imageName) throws ServiceException {
        String resultMessage = MessageKey.ERROR_MESSAGE_WRONG_FILE_TYPE;
        try {
            if (NumberValidator.isLongValid(id)) {
                Optional<Product> optionalProduct = productDao.findEntityById(Long.valueOf(id));
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setImageName(imageName);
                    if (productDao.update(product)) {
                        resultMessage = MessageKey.SUCCESSFUL_CHANGE_IMAGE;
                    } else {
                        resultMessage = MessageKey.ERROR_UNKNOWN_PRODUCT;
                    }
                } else {
                    resultMessage = MessageKey.ERROR_UNKNOWN_PRODUCT;
                }
            } else {
                resultMessage = MessageKey.ERROR_MESSAGE_INVALID_PARAM;
            }
        } catch (Exception e) {
            log.error(e);
            throw new ServiceException(e.getMessage(), e);
        }
        return resultMessage;
    }

    @Override
    public List<Product> searchProduct(String searchRequest) throws ServiceException {
        try {
            if (searchRequest.contains(" ")) {
                String[] search = searchRequest.split(" ");
                for (int i = 0; i < search.length; i++) {
                    search[i] = "%" + search[i] + "%";
                }
                return productDao.findProductByName(search);
            } else {
                return productDao.findProductByName("%" + searchRequest + "%");
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException();
        }
    }
}
