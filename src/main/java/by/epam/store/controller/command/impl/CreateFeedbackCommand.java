package by.epam.store.controller.command.impl;

import by.epam.store.controller.command.Command;
import by.epam.store.controller.Router;
import by.epam.store.model.entity.Product;
import by.epam.store.model.entity.User;
import by.epam.store.exception.ServiceException;
import by.epam.store.model.service.FeedbackService;
import by.epam.store.model.service.ProductService;
import by.epam.store.annotation.Autowired;
import by.epam.store.controller.command.PagePath;
import by.epam.store.controller.command.RequestParameterAndAttribute;
import by.epam.store.controller.command.SessionAttribute;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Optional;

/**
 * The Create feedback command.
 */
public class CreateFeedbackCommand implements Command {
    private final static Logger log = LogManager.getLogger(CreateFeedbackCommand.class);
    private FeedbackService feedbackService;
    private ProductService productService;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Router execute(HttpServletRequest request) {
        Router page;
        Map<String, String[]> parameters = RequestUtil.getAllParametersFrom(request);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(SessionAttribute.USER);
        try {
            Optional<String> optionalMessage = feedbackService.createFeedback(parameters, user);
            String currentPage = session.getAttribute(SessionAttribute.PAGE).toString();
            if (optionalMessage.isPresent()) {
                for (Map.Entry<String, String[]> entry : parameters.entrySet()) {
                    request.setAttribute(entry.getKey(), entry.getValue()[0]);
                }
                request.setAttribute(RequestParameterAndAttribute.MESSAGE, optionalMessage.get());
                String id = parameters.get(RequestParameterAndAttribute.ID_PRODUCT)[0];
                Optional<Product> optionalProduct = productService.findProductById(id);
                if (optionalProduct.isPresent()) {
                    Product product = optionalProduct.get();
                    request.setAttribute(RequestParameterAndAttribute.PRODUCT, product);
                    page = Router.forwardTo(PagePath.SINGLE_PRODUCT);
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
