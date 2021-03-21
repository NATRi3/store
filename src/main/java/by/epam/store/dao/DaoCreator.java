package by.epam.store.dao;

import by.epam.store.dao.impl.*;
import by.epam.store.dao.impl.BaseCollectionDao;
import by.epam.store.dao.impl.BaseFeedbackDao;
import by.epam.store.dao.impl.BaseNewsDao;
import by.epam.store.dao.impl.BaseOrderDao;
import by.epam.store.dao.impl.BaseProductDao;
import by.epam.store.dao.impl.BaseUserDao;

/**
 * The type Dao creator.
 */
public class DaoCreator {
    private static final DaoCreator instance = new DaoCreator();
    private final NewsDao newsDao = new BaseNewsDao();
    private final UserDao userDao = new BaseUserDao();
    private final ProductDao productDao = new BaseProductDao();
    private final OrderDao orderDao = new BaseOrderDao();
    private final FeedbackDao feedbackDao = new BaseFeedbackDao();
    private final CollectionDao collectionDao = new BaseCollectionDao();
    private final OrderDao noSQLOrderDao = new NoSqlOrderDao();

    private DaoCreator() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static DaoCreator getInstance() {
        return instance;
    }

    /**
     * Gets news dao.
     *
     * @return the news dao
     */
    public NewsDao getNewsDao() {
        return newsDao;
    }

    /**
     * Gets user dao.
     *
     * @return the user dao
     */
    public UserDao getUserDao() {
        return userDao;
    }

    /**
     * Gets product dao.
     *
     * @return the product dao
     */
    public ProductDao getProductDao() {
        return productDao;
    }

    /**
     * Gets no sql order dao.
     *
     * @return the no sql order dao
     */
    public OrderDao getNoSQLOrderDao() {
        return noSQLOrderDao;
    }

    /**
     * Gets order dao.
     *
     * @return the order dao
     */
    public OrderDao getOrderDao() {
        return orderDao;
    }

    /**
     * Gets feedback dao.
     *
     * @return the feedback dao
     */
    public FeedbackDao getFeedbackDao() {
        return feedbackDao;
    }

    /**
     * Gets collection dao.
     *
     * @return the collection dao
     */
    public CollectionDao getCollectionDao() {
        return collectionDao;
    }
}
