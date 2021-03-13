package by.epam.store.service;


import by.epam.store.service.impl.*;

public class ServiceCreator {
    private static final ServiceCreator instance = new ServiceCreator();

    private final UserService baseUserService = new ValidationUserService(new BaseUserService());
    private final ProductService baseProductService = new ValidatorProductService(new BaseProductService());
    private final OrderService baseOrderService = new ValidationOrderService(new BaseOrderService());
    private final CollectionService collectionService = new ValidationProductCollectionService(new BaseProductCollectionService());
    private final FeedbackService baseFeedbackService = new ValidationFeedbackService(new BaseFeedbackService());
    private final NewsService baseNewsService = new ValidationNewsService(new BaseNewsService());

    public static ServiceCreator getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return baseUserService;
    }

    public ProductService getProductService() {
        return baseProductService;
    }

    public OrderService getOrderService() {
        return baseOrderService;
    }

    public CollectionService getCollectionService() {
        return collectionService;
    }

    public FeedbackService getFeedbackService() {
        return baseFeedbackService;
    }

    public NewsService getNewsService() {
        return baseNewsService;
    }
}
