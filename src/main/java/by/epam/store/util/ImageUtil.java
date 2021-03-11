package by.epam.store.util;

import by.epam.store.controller.Router;
import by.epam.store.exception.ServiceException;
import by.epam.store.service.ImageService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class ImageUtil {

    public static Router changeImage(HttpServletRequest request,
                                     ImageService imageService,
                                     String id,
                                     Optional<String> imageName,
                                     String page,
                                     String optionalEmptyMessage) throws ServiceException {
        Router router;
        if (imageName.isPresent()) {
            String message = imageService.changeImage(id, imageName.get());
            request.setAttribute(RequestParameterAndAttribute.MESSAGE,message);
            router = RouterResponseHelper.router(request, message, page);
        } else {
            request.setAttribute(RequestParameterAndAttribute.MESSAGE, optionalEmptyMessage);
            router = Router.forwardTo(page, request);
        }
        return router;
    }

}
