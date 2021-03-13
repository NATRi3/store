package by.epam.store.validator;

public class NumberValidator {
    private static final String numberRegex = "\\d+";
    private static final String decimalRegex = "\\d+\\.?\\d{0,2}?";

    public static boolean isNumberValidForBigDecimal(String numberStr) {
        if (numberStr == null) return false;
        if (numberStr.length() > 17) return false;
        return numberStr.matches(decimalRegex);
    }

    public static boolean isLongValid(String numberStr) {
        if (numberStr == null) return false;
        if (numberStr.length() > 19) return false;
        return numberStr.matches(numberRegex);
    }
}
