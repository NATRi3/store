package by.epam.store.command.impl;

import by.epam.store.command.Command;
import by.epam.store.command.ServiceCreator;
import by.epam.store.controller.Router;
import by.epam.store.entity.Product;
import by.epam.store.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.impl.FeedbackService;
import by.epam.store.service.impl.ProductService;
import by.epam.store.util.PagePath;
import by.epam.store.util.RequestParameterAndAttribute;
import by.epam.store.util.RequestUtil;
import by.epam.store.util.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CreateFeedbackCommand implements Command {
    private final static Logger log = LogManager.getLogger(CreateFeedbackCommand.class);
    private static final FeedbackService feedbackService = ServiceCreator.getInstance().getFeedbackService();
    private static final ProductService productService = ServiceCreator.getInstance().getProductService();

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String> parameters = RequestUtil.getAllParametersFrom(request);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            Optional<String> optionalMessage = feedbackService.createFeedback(parameters, user);
            String currentPage = session.getAttribute(SessionAttribute.PAGE).toString();
            if (optionalMessage.isPresent()) {
                for (Map.Entry<String, String> entry : parameters.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue());
                }
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, optionalMessage.get());
                String id = parameters.get(RequestParameterAndAttribute.ID_PRODUCT);
                Optional<Product> optionalProduct = productService.findProductById(id);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    request.setAttribute(RequestParameterAndAttribute.PRODUCT, product);
                    page = Router.forwardTo(PagePath.SINGLE_PRODUCT, request);
                } else {
                    page = Router.redirectTo(PagePath.PAGE_404, request);
                }
            } else {
                page = Router.redirectTo(currentPage, request);
            }
        } catch (ServiceException e) {
            log.error(e);
            page = Router.redirectTo(PagePath.PAGE_500, request);
        }
        return page;
    }
}
