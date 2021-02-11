package by.epam.store.service;

import by.epam.store.dao.impl.*;

public interface BaseService {
    static final NewsDao newsDao = new NewsDao();
    static final UserDao userDao = new UserDao();
    static final ProductDao productDao = new ProductDao();
    static final OrderDao orderDao = new OrderDao();
    static final FeedbackDao feedbackDao = new FeedbackDao();
    static final CollectionDao collectionDao = new CollectionDao();
}
