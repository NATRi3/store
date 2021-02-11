package by.epam.store.validator;

public class NumberValidator {
    private static final String numberRegex = "\\d*";
    private static final String decimalRegex = "\\d*+(\\.+\\d*)?";
    public static boolean isNumberValidForBigDecimal(String numberStr){
        if(numberStr==null)return false;
        return numberStr.matches(decimalRegex);
    }
    public static boolean isNumberValid(String numberStr){
        if(numberStr==null)return false;
        return numberStr.matches(numberRegex);
    }
}
