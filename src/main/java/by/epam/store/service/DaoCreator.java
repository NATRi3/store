package by.epam.store.service;

import by.epam.store.dao.impl.*;

public class DaoCreator {
    private static final DaoCreator INSTANCE = new DaoCreator();
    private final NewsDao newsDao = new NewsDao();
    private final UserDao userDao = new UserDao();
    private final ProductDao productDao = new ProductDao();
    private final OrderDao orderDao = new OrderDao();
    private final FeedbackDao feedbackDao = new FeedbackDao();
    private final CollectionDao collectionDao = new CollectionDao();

    private DaoCreator(){  }

    public static DaoCreator getInstance(){
        return INSTANCE;
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
