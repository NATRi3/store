package by.epam.store.dao;

import by.epam.store.dao.impl.*;
import by.epam.store.dao.impl.BaseCollectionDao;
import by.epam.store.dao.impl.BaseFeedbackDao;
import by.epam.store.dao.impl.BaseNewsDao;
import by.epam.store.dao.impl.BaseOrderDao;
import by.epam.store.dao.impl.BaseProductDao;
import by.epam.store.dao.impl.BaseUserDao;

public class DaoCreator {
    private static final DaoCreator instance = new DaoCreator();
    private final NewsDao newsDao = new BaseNewsDao();
    private final UserDao userDao = new BaseUserDao();
    private final ProductDao productDao = new BaseProductDao();
    private final OrderDao orderDao = new BaseOrderDao();
    private final FeedbackDao feedbackDao = new BaseFeedbackDao();
    private final CollectionDao collectionDao = new BaseCollectionDao();
    private final OrderDao noSQLOrderDao = new NoSQLDao();

    private DaoCreator() {
    }

    public static DaoCreator getInstance() {
        return instance;
    }

    public NewsDao getNewsDao() {
        return newsDao;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public ProductDao getProductDao() {
        return productDao;
    }

    public OrderDao getNoSQLOrderDao() {
        return noSQLOrderDao;
    }

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public FeedbackDao getFeedbackDao() {
        return feedbackDao;
    }

    public CollectionDao getCollectionDao() {
        return collectionDao;
    }
}
