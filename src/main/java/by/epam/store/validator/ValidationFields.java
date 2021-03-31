package by.epam.store.validator;

import java.util.Arrays;
import java.util.function.Predicate;

/**
 * The enum Validation fields.
 */
public enum ValidationFields {
    /**
     * Name collection validation fields.
     */
    NAME_COLLECTION(x -> FormValidator.isStringLess(x, FormValidator.MAX_NAME_SIZE)),
    /**
     * Info collection validation fields.
     */
    INFO_COLLECTION(x -> FormValidator.isStringLess(x, FormValidator.MAX_INFO_500_SIZE)),
    /**
     * News title validation fields.
     */
    NEWS_TITLE(x -> FormValidator.isStringLess(x, FormValidator.MAX_TITLE_NEWS_SIZE)),
    /**
     * Id collection validation fields.
     */
    ID_COLLECTION(NumberValidator::isLongValid),
    /**
     * News info validation fields.
     */
    NEWS_INFO(x -> FormValidator.isStringLess(x, FormValidator.MAX_INFO_SIZE)),
    /**
     * Info product validation fields.
     */
    INFO_PRODUCT(x -> FormValidator.isStringLess(x, FormValidator.MAX_INFO_SIZE)),
    /**
     * Password validation fields.
     */
    PASSWORD(FormValidator::isPasswordValid),
    /**
     * Repeat password validation fields.
     */
    REPEAT_PASSWORD(FormValidator::isPasswordValid),
    /**
     * Email validation fields.
     */
    EMAIL(FormValidator::isEmailValid),
    /**
     * Name validation fields.
     */
    NAME(x -> FormValidator.isStringLess(x, FormValidator.MAX_NAME_SIZE)),
    /**
     * Name product validation fields.
     */
    NAME_PRODUCT(x -> FormValidator.isStringLess(x, FormValidator.MAX_NAME_SIZE)),
    /**
     * Price product validation fields.
     */
    PRICE_PRODUCT(NumberValidator::isNumberValidForBigDecimal),
    /**
     * Id product validation fields.
     */
    ID_PRODUCT(NumberValidator::isLongValid),
    /**
     * Feedback validation fields.
     */
    FEEDBACK(x -> FormValidator.isStringLess(x, FormValidator.MAX_FEEDBACK_SIZE)),
    /**
     * Evaluation validation fields.
     */
    EVALUATION(x -> NumberValidator.isLongValid(x) && (Integer.parseInt(x) >= 1 && Integer.parseInt(x) <= 5)),
    /**
     * Phone validation fields.
     */
    PHONE(FormValidator::isPhoneValid),
    /**
     * Address validation fields.
     */
    ADDRESS(x -> FormValidator.isStringLess(x, FormValidator.MAX_ADDRESS_SIZE));

    private final Predicate<String> validation;

    ValidationFields(Predicate<String> validation) {
        this.validation = validation;
    }

    /**
     * Is String validation field.
     *
     * @param fieldName the field name
     * @return the boolean
     */
    static boolean isValidationField(String fieldName) {
        return Arrays.stream(ValidationFields.values()).anyMatch(valid->valid.name().equals(fieldName));
    }

    /**
     * Gets validation.
     *
     * @return the validation
     */
    public Predicate<String> getValidation() {
        return validation;
    }

    /**
     * Is field valid.
     *
     * @param field the field
     * @return the boolean
     */
    public boolean isValid(String field){
        return validation.test(field);
    }
}
