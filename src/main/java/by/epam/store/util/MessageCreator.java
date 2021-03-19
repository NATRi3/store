package by.epam.store.util;

import by.epam.store.command.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;


/**
 * The type Message creator.
 */
public class MessageCreator {
    private static final Map<String, ResourceBundle> bundleMap = new HashMap<>();
    /**
     * The constant instance.
     */
    public static final MessageCreator instance = new MessageCreator();

    private MessageCreator() {
        bundleMap.put("en-Us", ResourceBundle.getBundle("property/error", Locale.US));
        bundleMap.put("ru-RU", ResourceBundle.getBundle("property/error", Locale.forLanguageTag("ru-Ru")));
        bundleMap.put("default", ResourceBundle.getBundle("property/error", Locale.getDefault()));
    }

    /**
     * Gets message from bundle by locale.
     *
     * @param key     the key
     * @param request the request
     * @return the message from bundle by locale
     */
    public String getMessageFromBundleByLocale(String key, HttpServletRequest request) {
        String locale = String.valueOf(request.getSession().getAttribute(SessionAttribute.LOCALE));
        ResourceBundle bundle = bundleMap.get(locale);
        if (bundle == null) {
            bundle = bundleMap.get("default");
        }
        return bundle.getString(key);
    }
}
