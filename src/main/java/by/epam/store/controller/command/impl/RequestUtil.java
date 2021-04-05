package by.epam.store.controller.command.impl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static by.epam.store.controller.command.RequestParameterAndAttribute.*;

public class RequestUtil {

    public static final Set<String> setParametersName = Set.of(
            NAME, PASSWORD, EMAIL, REPEAT_PASSWORD, NEW_LOCALE, ACTIVATION_CODE, NAME_PRODUCT,
            INFO_PRODUCT, PRICE_PRODUCT, CHANGE_PASSWORD_OLD, TYPE_SORT,
            ID_COLLECTION, BEGIN_PAGINATION, PRODUCT_AMOUNT, ID_PRODUCT, TYPE_STATUS, NEWS_AMOUNT, ID_NEWS, NEWS_TITLE,
            NEWS_INFO, PHONE, ADDRESS, EVALUATION, INFO_COLLECTION, NAME_COLLECTION, ID_USER, ID_FEEDBACK,
            CLIENT_TOKEN, SEARCH, FEEDBACK, MESSAGE, PRODUCT
    );

    public static final Map<String, String> replaceFields = Map.of(REPEAT_PASSWORD, PASSWORD);

    static Map<String, String[]> getAllParametersFrom(HttpServletRequest request) {
        return getAllParametersFrom(request, setParametersName);
    }

    static Map<String, String[]> getAllParametersFrom(HttpServletRequest request,
                                                      Set<String> setParametersName) {
        Map<String, String[]> parameters =
                request.getParameterMap()
                        .entrySet().stream()
                        .filter(e -> setParametersName.contains(e.getKey()))
                        .filter(e -> !replaceFields.containsKey(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        for (Map.Entry<String, String> entry : replaceFields.entrySet()) {
            if(parameters.containsKey(entry.getKey()) && parameters.containsKey(entry.getValue())) {
                String[] oldValue = parameters.get(entry.getValue());
                String[] newValue = new String[oldValue.length + 1];
                System.arraycopy(oldValue, 0, newValue, 0, oldValue.length);
                newValue[oldValue.length] = request.getParameter(entry.getKey());
                parameters.remove(entry.getKey());
                parameters.replace(entry.getValue(), newValue);
            }
        }
        return parameters;
    }
}
