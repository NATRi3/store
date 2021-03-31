package by.epam.store.validator;

import java.util.*;

/**
 * The type Form validator.
 */
public class FormValidator {
    static final int MAX_FEEDBACK_SIZE = 200;
    /**
     * The Max title news size.
     */
    static final int MAX_TITLE_NEWS_SIZE = 70;
    /**
     * The Max name size.
     */
    static final int MAX_NAME_SIZE = 45;
    /**
     * The Max info size.
     */
    static final int MAX_INFO_SIZE = 1000;
    /**
     * The Max address size.
     */
    static final int MAX_ADDRESS_SIZE = 70;
    /**
     * The Max info size (500) for collections.
     */
    static final int MAX_INFO_500_SIZE = 500;


    private static final String EMAIL_REGEX = "([\\w]+\\.)*[\\w]+@[\\w]+(\\.[\\w]+)*\\.[\\w]{2,6}";
    private static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,40}";
    private static final String PHONE_REGEX = "\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";
    private static final EnumSet<ValidationFields> VALIDATION_FIELDS = EnumSet.allOf(ValidationFields.class);

    /**
     * Validate parameters in map. Remove invalid fields.
     *
     * @param dataMap the data map
     * @return the boolean
     */
    public static boolean isFormValid(Map<String, String> dataMap) {
        boolean valid = true;
        List<String> removeKeyList = new ArrayList<>();
        VALIDATION_FIELDS.stream()
                .filter(e->dataMap.containsKey(e.name().toLowerCase()))
                .filter(e->!e.isValid(dataMap.get(e.name().toLowerCase())))
                .forEach(e->removeKeyList.add(e.name().toLowerCase()));
        for (String key : removeKeyList) {
            valid = false;
            dataMap.remove(key);
        }
        return valid;
    }

    /**
     * Is phone valid boolean.
     *
     * @param phone the phone
     * @return the boolean
     */
    static boolean isPhoneValid(String phone) {
        if (phone == null) return false;
        return phone.matches(PHONE_REGEX);
    }

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.matches(EMAIL_REGEX);
    }

    /**
     * Is password valid boolean.
     *
     * @param password the password
     * @return the boolean
     */
    static boolean isPasswordValid(String password) {
        if (password == null) return false;
        return password.matches(PASSWORD_REGEX);
    }

    /**
     * Is string less boolean.
     *
     * @param title the title
     * @param size  the size
     * @return the boolean
     */
    static boolean isStringLess(String title, int size) {
        if (title == null) return false;
        return title.length() < size;
    }
}