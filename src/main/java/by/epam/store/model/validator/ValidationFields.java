package by.epam.store.model.validator;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The enum Validation fields.
 */
public enum ValidationFields {
    /**
     * Name collection validation fields.
     */
    NAME_COLLECTION(x -> isStringLess(x[0], ValidationConstant.MAX_NAME_SIZE)),
    /**
     * Info collection validation fields.
     */
    INFO_COLLECTION(x -> isStringLess(x[0], ValidationConstant.MAX_INFO_500_SIZE)),
    /**
     * News title validation fields.
     */
    NEWS_TITLE(x -> isStringLess(x[0], ValidationConstant.MAX_TITLE_NEWS_SIZE)),
    /**
     * Id collection validation fields.
     */
    ID_COLLECTION(x->NumberValidator.isLongValid(x[0])),
    /**
     * News info validation fields.
     */
    NEWS_INFO(x -> isStringLess(x[0], ValidationConstant.MAX_INFO_SIZE)),
    /**
     * Info product validation fields.
     */
    INFO_PRODUCT(x -> isStringLess(x[0], ValidationConstant.MAX_INFO_SIZE)),
    /**
     * Password validation fields.
     */
    PASSWORD(x -> x[0].matches(ValidationConstant.PASSWORD_REGEX) && x.length==1 || x[0].equals(x[1])),
    /**
     * Email validation fields.
     */
    EMAIL(x -> x[0].matches(ValidationConstant.EMAIL_REGEX)),
    /**
     * Name validation fields.
     */
    NAME(x -> ValidationFields.isStringLess(x[0], ValidationConstant.MAX_NAME_SIZE)),
    /**
     * Name product validation fields.
     */
    NAME_PRODUCT(x -> ValidationFields.isStringLess(x[0], ValidationConstant.MAX_NAME_SIZE)),
    /**
     * Price product validation fields.
     */
    PRICE_PRODUCT(x->NumberValidator.isNumberValidForBigDecimal(x[0])),
    /**
     * Id product validation fields.
     */
    ID_PRODUCT(x->NumberValidator.isLongValid(x[0])),
    /**
     * Feedback validation fields.
     */
    FEEDBACK(x -> isStringLess(x[0], ValidationConstant.MAX_FEEDBACK_SIZE)),
    /**
     * Evaluation validation fields.
     */
    EVALUATION(x -> NumberValidator.isLongValid(x[0]) && (Integer.parseInt(x[0]) >= 1 && Integer.parseInt(x[0]) <= 5)),
    /**
     * Phone validation fields.
     */
    PHONE(x -> x[0].matches(ValidationConstant.PHONE_REGEX)),
    /**
     * Address validation fields.
     */
    ADDRESS(x -> isStringLess(x[0], ValidationConstant.MAX_ADDRESS_SIZE));

    private static final EnumSet<ValidationFields> VALIDATION_FIELDS = EnumSet.allOf(ValidationFields.class);
    private final Predicate<String[]> validation;

    ValidationFields(Predicate<String[]> validation) {
        this.validation = validation;
    }

    /**
     * Is map parameters valid.
     *
     * @param dataMap the data map
     * @return the boolean
     */
    public static boolean isMapParametersValid(Map<String, String[]> dataMap) {
        boolean valid = true;
        List<String> removeKeyList = VALIDATION_FIELDS.stream()
                .filter(e -> dataMap.containsKey(e.name().toLowerCase()))
                .filter(e -> !e.isValid(dataMap.get(e.name().toLowerCase())))
                .map(Enum::name)
                .collect(Collectors.toList());
        for (String key : removeKeyList) {
            valid = false;
            dataMap.remove(key.toLowerCase());
        }
        return valid;
    }

    /**
     * Gets validation.
     *
     * @return the validation
     */
    public Predicate<String[]> getValidation() {
        return validation;
    }

    /**
     * Is field valid.
     *
     * @param field the field
     * @return the boolean
     */
    public boolean isValid(String[] field){
        if(field==null) return false;
        if(field.length==0) return false;
        return validation.test(field);
    }

    /**
     * Is field valid.
     *
     * @param field the field
     * @return the boolean
     */
    public boolean isValid(String field){
        if(field==null) return false;
        return validation.test(new String[]{field});
    }

    private static boolean isStringLess(String title, int size) {
        return title.length() < size;
    }
}
