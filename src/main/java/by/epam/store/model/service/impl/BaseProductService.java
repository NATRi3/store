package by.epam.store.model.service.impl;

import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.model.dao.ProductDao;
import by.epam.store.model.dao.impl.BaseProductDao;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.TypeStatus;
import by.epam.store.exception.DaoException;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.ProductService;
import by.epam.store.annotation.Autowired;
import by.epam.store.annotation.Bean;
import by.epam.store.util.MessageKey;
import by.epam.store.model.validator.NumberValidator;
import by.epam.store.model.validator.TypeValidator;
import by.epam.store.model.validator.ValidationFields;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The type Base product service.
 */
@Bean
public class BaseProductService implements ProductService {
    private static final Logger log = LogManager.getLogger(BaseProductService.class);
    private ProductDao baseProductDao;

    /**
     * Instantiates a new Base product service.
     */
    public BaseProductService() {

    }

    /**
     * Instantiates a new Base product service.
     *
     * @param baseProductDao the base product dao
     */
    public BaseProductService(BaseProductDao baseProductDao) {
        this.baseProductDao = baseProductDao;
    }

    @Override
    public Optional<Product> findProductById(String idProduct) throws ServiceException {
        try {
            if (NumberValidator.isLongValid(idProduct)) {
                long id = Long.parseLong(idProduct);
                return baseProductDao.findEntityById(id);
            } else {
                return Optional.empty();
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String saveProduct(Map<String, String[]> parameters) throws ServiceException {
        if (!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.ID_COLLECTION) ||
                !parameters.containsKey(RequestParameterAndAttribute.PRICE_PRODUCT) ||
                !parameters.containsKey(RequestParameterAndAttribute.NAME_PRODUCT) ||
                !parameters.containsKey(RequestParameterAndAttribute.INFO_PRODUCT)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        try {
            long idCollection = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_COLLECTION)[0]);
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(parameters.get(RequestParameterAndAttribute.PRICE_PRODUCT)[0]));
            String name = parameters.get(RequestParameterAndAttribute.NAME_PRODUCT)[0];
            String info = parameters.get(RequestParameterAndAttribute.INFO_PRODUCT)[0];
            Product product = Product.builder()
                    .idCollection(idCollection)
                    .price(price)
                    .name(name)
                    .info(info)
                    .build();
            baseProductDao.create(product);
            return MessageKey.SUCCESSFUL_PRODUCT_ADD;
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeStatus(String idProduct, TypeStatus status) throws ServiceException {
        if (!NumberValidator.isLongValid(idProduct)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        String resultMessage;
        try {
            long id = Long.parseLong(idProduct);
            Optional<Product> optionalProduct = baseProductDao.findEntityById(id);
            if (optionalProduct.isPresent()) {
                Product product = optionalProduct.get();
                product.setStatus(status);
                baseProductDao.update(product);
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
        try {
            int beginPagination = Integer.parseInt(begin);
            TypeStatus typeStatus = TypeStatus.valueOf(status);
            return baseProductDao.findCollectionProductAndSort(beginPagination, typeStatus, idCollection, typeSort);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Product> findRandomProduct(String amountStr) throws ServiceException {
        if (!NumberValidator.isLongValid(amountStr)) {
            throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
        }
        try {
            if (!NumberValidator.isLongValid(amountStr)) {
                throw new ServiceException(MessageKey.ERROR_MESSAGE_INVALID_PARAM);
            }
            int amount = Integer.parseInt(amountStr);
            return baseProductDao.findRandomProduct(amount);
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException(e);
        }
    }

    @Override
    public String changeProduct(Map<String, String[]> parameters) throws ServiceException {
        if (!ValidationFields.isMapParametersValid(parameters) ||
                !parameters.containsKey(RequestParameterAndAttribute.ID_PRODUCT) ||
                !parameters.containsKey(RequestParameterAndAttribute.PRICE_PRODUCT) ||
                !parameters.containsKey(RequestParameterAndAttribute.INFO_PRODUCT)) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        String resultMessage;
        try {
            if (ValidationFields.isMapParametersValid(parameters)) {
                long idProduct = Long.parseLong(parameters.get(RequestParameterAndAttribute.ID_PRODUCT)[0]);
                BigDecimal priceProduct = BigDecimal.valueOf(Double.parseDouble(parameters.get(RequestParameterAndAttribute.PRICE_PRODUCT)[0]));
                String info = parameters.get(RequestParameterAndAttribute.INFO_PRODUCT)[0];
                Optional<Product> optionalProduct = baseProductDao.findEntityById(idProduct);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setInfo(info);
                    product.setPrice(priceProduct);
                    baseProductDao.update(product);
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
        if (!NumberValidator.isLongValid(id) || imageName == null) {
            return MessageKey.ERROR_MESSAGE_INVALID_PARAM;
        }
        String resultMessage;
        try {
            if (NumberValidator.isLongValid(id)) {
                Optional<Product> optionalProduct = baseProductDao.findEntityById(Long.valueOf(id));
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    product.setImageName(imageName);
                    if (baseProductDao.update(product)) {
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
                return baseProductDao.findProductByName(search);
            } else {
                return baseProductDao.findProductByName("%" + searchRequest + "%");
            }
        } catch (DaoException e) {
            log.error(e);
            throw new ServiceException();
        }
    }
    @Autowired
    public void setBaseProductDao(ProductDao baseProductDao) {
        this.baseProductDao = baseProductDao;
    }
}
