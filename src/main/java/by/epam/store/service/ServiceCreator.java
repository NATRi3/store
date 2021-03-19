package by.epam.store.service;


import by.epam.store.service.impl.*;

/**
 * The type Service creator.
 */
public class ServiceCreator {
    private static final ServiceCreator instance = new ServiceCreator();

    private final UserService baseUserService = new ValidationUserService(new BaseUserService());
    private final ProductService baseProductService = new ValidatorProductService(new BaseProductService());
    private final OrderService baseOrderService = new ValidationOrderService(new BaseOrderService());
    private final CollectionService collectionService = new ValidationProductCollectionService(new BaseProductCollectionService());
    private final FeedbackService baseFeedbackService = new ValidationFeedbackService(new BaseFeedbackService());
    private final NewsService baseNewsService = new ValidationNewsService(new BaseNewsService());

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static ServiceCreator getInstance() {
        return instance;
    }

    /**
     * Gets user service.
     *
     * @return the user service
     */
    public UserService getUserService() {
        return baseUserService;
    }

    /**
     * Gets product service.
     *
     * @return the product service
     */
    public ProductService getProductService() {
        return baseProductService;
    }

    /**
     * Gets order service.
     *
     * @return the order service
     */
    public OrderService getOrderService() {
        return baseOrderService;
    }

    /**
     * Gets collection service.
     *
     * @return the collection service
     */
    public CollectionService getCollectionService() {
        return collectionService;
    }

    /**
     * Gets feedback service.
     *
     * @return the feedback service
     */
    public FeedbackService getFeedbackService() {
        return baseFeedbackService;
    }

    /**
     * Gets news service.
     *
     * @return the news service
     */
    public NewsService getNewsService() {
        return baseNewsService;
    }
}
