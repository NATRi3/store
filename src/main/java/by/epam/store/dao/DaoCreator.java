package by.epam.store.dao;

import by.epam.store.dao.impl.CollectionDao;
import by.epam.store.dao.impl.FeedbackDao;
import by.epam.store.dao.impl.NewsDao;
import by.epam.store.dao.impl.OrderDao;
import by.epam.store.dao.impl.ProductDao;
import by.epam.store.dao.impl.UserDao;

public class DaoCreator {
    private static final DaoCreator instance = new DaoCreator();
    private final by.epam.store.dao.impl.NewsDao newsDao = new by.epam.store.dao.impl.NewsDao();
    private final by.epam.store.dao.impl.UserDao userDao = new by.epam.store.dao.impl.UserDao();
    private final by.epam.store.dao.impl.ProductDao productDao = new by.epam.store.dao.impl.ProductDao();
    private final by.epam.store.dao.impl.OrderDao orderDao = new by.epam.store.dao.impl.OrderDao();
    private final by.epam.store.dao.impl.FeedbackDao feedbackDao = new by.epam.store.dao.impl.FeedbackDao();
    private final by.epam.store.dao.impl.CollectionDao collectionDao = new by.epam.store.dao.impl.CollectionDao();

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
