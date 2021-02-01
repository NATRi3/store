package by.epam.store.validator;

public class UserValidator {
    private static final String EMAIL_REGEX = "([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}";
    private static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,40}";
    public static boolean isEmailValid(String email){
        return email.matches(EMAIL_REGEX);
    }
    public static boolean isPasswordValid(String password){
        return password.matches(PASSWORD_REGEX);
    }
}
