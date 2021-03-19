package by.epam.store.command.impl;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static by.epam.store.command.RequestParameterAndAttribute.*;

public class RequestUtil {

    private static final Set<String> setParametersName = Set.of(
            NAME, PASSWORD, EMAIL, REPEAT_PASSWORD, NEW_LOCALE, ACTIVATION_CODE, NAME_PRODUCT,
            INFO_PRODUCT, PRICE_PRODUCT, CHANGE_PASSWORD_OLD, TYPE_SORT,
            ID_COLLECTION, BEGIN_PAGINATION, PRODUCT_AMOUNT, ID_PRODUCT, TYPE_STATUS, NEWS_AMOUNT, ID_NEWS, NEWS_TITLE,
            NEWS_INFO, PHONE, ADDRESS, EVALUATION, INFO_COLLECTION, NAME_COLLECTION, ID_USER, ID_FEEDBACK,
            CLIENT_TOKEN, SEARCH, FEEDBACK, MESSAGE, PRODUCT
    );

    static Map<String, String> getAllParametersFrom(HttpServletRequest request) {
        return getAllParametersFrom(request, setParametersName);
    }

    static Map<String, String> getAllParametersFrom(HttpServletRequest request,
                                                           Set<String> setParametersName) {
        Map<String, String> parameters = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            if (setParametersName.contains(parameterName)) {
                String parameter = request.getParameter(parameterName);
                parameters.put(parameterName, parameter);
            }
        }
        return parameters;
    }


}
