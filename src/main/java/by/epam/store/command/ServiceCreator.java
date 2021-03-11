package by.epam.store.command;


import by.epam.store.service.impl.*;

public class ServiceCreator {
    private static final ServiceCreator INSTANCE = new ServiceCreator();

    private final UserService userService = new UserService();
    private final ProductService productService = new ProductService();
    private final OrderService orderService = new OrderService();
    private final ProductCollectionService collectionService = new ProductCollectionService();
    private final FeedbackService feedbackService = new FeedbackService();
    private final NewsService newsService = new NewsService();

    public static ServiceCreator getInstance() {
        return INSTANCE;
    }

    public UserService getUserService() {
        return userService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public OrderService getOrderService() {
        return orderService;
    }

    public ProductCollectionService getCollectionService() {
        return collectionService;
    }

    public FeedbackService getFeedbackService() {
        return feedbackService;
    }

    public NewsService getNewsService() {
        return newsService;
    }
}
