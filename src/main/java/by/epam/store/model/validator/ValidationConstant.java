package by.epam.store.model.validator;

/**
 * The type Form validator.
 */
public class ValidationConstant {
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


    static final String EMAIL_REGEX = "([\\w]+\\.)*[\\w]+@[\\w]+(\\.[\\w]+)*\\.[\\w]{2,6}";
    static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,40}";
    static final String PHONE_REGEX = "\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";
}