package by.epam.store.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class MessageCreator {
    private static final Map<String, ResourceBundle> bundleMap = new HashMap<>();
    static {
        bundleMap.put("en-Us", ResourceBundle.getBundle("property/error", Locale.US));
        bundleMap.put("ru-RU",ResourceBundle.getBundle("property/error", Locale.forLanguageTag("ru-Ru")));
        bundleMap.put("default",ResourceBundle.getBundle("property/error",Locale.getDefault()));
    }

    public static String getMessageFromBundleByLocale(String key, HttpServletRequest request){
        String locale = String.valueOf(request.getSession().getAttribute(SessionAttribute.LOCALE));
        ResourceBundle bundle = bundleMap.get(locale);
        if(bundle==null){
            bundle = bundleMap.get("default");
        }
        return bundle.getString(key);
    }
}
