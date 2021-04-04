package by.epam.store.model.validator;

import by.epam.store.model.entity.User;

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
        return ValidationFields.EMAIL.isValid(user.getEmail())
                && (user.getId() > MIN_USER_ID);
    }

}
