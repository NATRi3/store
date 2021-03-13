package by.epam.store.util;

import by.epam.store.entity.User;
import by.epam.store.validator.FormValidator;

public class EntityValidator {

    public static final int MIN_USER_ID = 0;

    public static boolean isUserValid(User user) {
        return FormValidator.isEmailValid(user.getEmail())
                && (user.getId() < MIN_USER_ID);
    }

}
