package by.epam.store.command;

import by.epam.store.service.impl.*;

public interface BaseCommand {
    static final UserService userService = new UserService();
    static final FeedbackService feedbackService = new FeedbackService();
    static final ProductService productService = new ProductService();
    static final OrderService orderService = new OrderService();
    static final NewsService newsService = new NewsService();
    static final ProductCollectionService productCollectionService = new ProductCollectionService();
}
