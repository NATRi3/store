package by.epam.store.validator;

import by.epam.store.entity.User;
import by.epam.store.validator.FormValidator;

/**
 * The type Entity validator.
 */
public class UserValidator {

    /**
     * The constant MIN_USER_ID.
     */
    public static final int MIN_USER_ID = 0;

    /**
     * Is user valid boolean.
     *
     * @param user the user
     * @return the boolean
     */
    public static boolean isUserValid(User user) {
        return FormValidator.isEmailValid(user.getEmail())
                && (user.getId() > MIN_USER_ID);
    }

}
