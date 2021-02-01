package by.epam.store.command;

import by.epam.store.service.impl.FeedbackService;
import by.epam.store.service.impl.OrderService;
import by.epam.store.service.impl.UserService;
import by.epam.store.service.impl.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
    static final UserService userService = new UserService();
    static final FeedbackService feedbackService = new FeedbackService();
    static final ProductService productService = new ProductService();
    static final OrderService orderService = new OrderService();
    public String execute(HttpServletRequest request);
}
