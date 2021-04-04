package by.epam.store.model.validator;

import java.util.Set;

public class LocalValidator {
    private static final Set<String> availableLocal = Set.of(
            "en-US",
            "ru-RU"
    );

    public static boolean isLocale(String local) {
        if (local == null) return false;
        return availableLocal.contains(local);
    }
}
