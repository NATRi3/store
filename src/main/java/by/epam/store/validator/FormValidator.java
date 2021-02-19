package by.epam.store.validator;

import by.epam.store.util.RequestParameter;

import java.util.Map;

public class FormValidator {
    private static final int MAX_FEEDBACK_SIZE = 200;
    private static final int MAX_TITLE_NEWS_SIZE = 70;
    private static final int MAX_NAME_SIZE = 45;
    private static final int MAX_INFO_SIZE = 1000;
    private static final String EMAIL_REGEX = "([A-Za-z0-9_-]+\\.)*[A-Za-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}";
    private static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,40}";
    private static final String PHONE_REGEX = "\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";
    private static final int MAX_ADDRESS_SIZE = 70;
    private static final int MAX_INFO_500_SIZE = 500;

    public static boolean isFormValid(Map<String, String> dataMap) {
        boolean valid = true;
        if (dataMap.containsKey(RequestParameter.NAME_COLLECTION)) {
            String title = dataMap.get(RequestParameter.NAME_COLLECTION);
            if(!isStringLess(title,MAX_NAME_SIZE)){
                dataMap.remove(RequestParameter.NAME_COLLECTION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.INFO_COLLECTION)) {
            String title = dataMap.get(RequestParameter.INFO_COLLECTION);
            if(!isStringLess(title,MAX_INFO_500_SIZE)){
                dataMap.remove(RequestParameter.INFO_COLLECTION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.NEWS_TITLE)) {
            String title = dataMap.get(RequestParameter.NEWS_TITLE);
            if(!isStringLess(title,MAX_TITLE_NEWS_SIZE)){
                dataMap.remove(RequestParameter.NEWS_TITLE);
                valid = false;
            }
        }
        if(dataMap.containsKey(RequestParameter.ID_COLLECTION)){
            String id = dataMap.get(RequestParameter.ID_COLLECTION);
            if(!NumberValidator.isNumberValid(id)){
                dataMap.remove(RequestParameter.ID_COLLECTION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.NEWS_INFO)) {
            String info = dataMap.get(RequestParameter.NEWS_INFO);
            if(!isStringLess(info,MAX_INFO_SIZE)){
                dataMap.remove(RequestParameter.NEWS_INFO);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.INFO_PRODUCT)) {
            String info = dataMap.get(RequestParameter.INFO_PRODUCT);
            if(!isStringLess(info,MAX_INFO_SIZE)){
                dataMap.remove(RequestParameter.INFO_PRODUCT);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.PASSWORD)) {
            String password = dataMap.get(RequestParameter.PASSWORD);
            if(!isPasswordValid(password)){
                dataMap.remove(RequestParameter.PASSWORD);
                valid = false;
            }
            if (dataMap.containsKey(RequestParameter.REPEAT_PASSWORD)) {
                String repeatPassword = dataMap.get(RequestParameter.PASSWORD);
                if(!password.equals(repeatPassword)){
                    dataMap.remove(RequestParameter.PASSWORD);
                    valid = false;
                }
            }
        }
        if (dataMap.containsKey(RequestParameter.EMAIL)) {
            String email = dataMap.get(RequestParameter.EMAIL);
            if(!isEmailValid(email)){
                dataMap.remove(RequestParameter.EMAIL);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.NAME)) {
            String name = dataMap.get(RequestParameter.NAME);
            if(!isStringLess(name,MAX_NAME_SIZE)){
                dataMap.remove(RequestParameter.NAME);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.NAME_PRODUCT)) {
            String name = dataMap.get(RequestParameter.NAME_PRODUCT);
            if(!isStringLess(name,MAX_NAME_SIZE)){
                dataMap.remove(RequestParameter.NAME_PRODUCT);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.NAME)) {
            String name = dataMap.get(RequestParameter.NAME);
            if(!isStringLess(name,MAX_NAME_SIZE)){
                dataMap.remove(RequestParameter.NAME);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.PRICE_PRODUCT)) {
            String price = dataMap.get(RequestParameter.PRICE_PRODUCT);
            if(!NumberValidator.isNumberValidForBigDecimal(price)){
                dataMap.remove(RequestParameter.PRICE_PRODUCT);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.REPEAT_PASSWORD)) {
            String pass = dataMap.get(RequestParameter.REPEAT_PASSWORD);
            if(!isPasswordValid(pass)){
                dataMap.remove(RequestParameter.REPEAT_PASSWORD);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.ID_PRODUCT)) {
            String idProduct = dataMap.get(RequestParameter.ID_PRODUCT);
            if(!NumberValidator.isNumberValid(idProduct)){
                dataMap.remove(RequestParameter.ID_PRODUCT);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.FEEDBACK)) {
            String feedback = dataMap.get(RequestParameter.FEEDBACK);
            if(!isStringLess(feedback,MAX_FEEDBACK_SIZE)){
                dataMap.remove(RequestParameter.FEEDBACK);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.EVALUATION)) {
            String evaluation = dataMap.get(RequestParameter.EVALUATION);
            if(!NumberValidator.isNumberValid(evaluation)||
                    !(Integer.parseInt(evaluation)>=1&&Integer.parseInt(evaluation)<=5)){
                dataMap.remove(RequestParameter.EVALUATION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.ID_COLLECTION)) {
            String idCollection = dataMap.get(RequestParameter.ID_COLLECTION);
            if(!NumberValidator.isNumberValid(idCollection)){
                dataMap.remove(RequestParameter.ID_COLLECTION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.PHONE)) {
            String phone = dataMap.get(RequestParameter.PHONE);
            if(!isPhoneValid(phone)){
                dataMap.remove(RequestParameter.PHONE);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameter.ADDRESS)) {
            String address = dataMap.get(RequestParameter.ADDRESS);
            if(!isStringLess(address,MAX_ADDRESS_SIZE)){
                dataMap.remove(RequestParameter.ADDRESS);
                valid = false;
            }
        }
        return valid;

    }

    private static boolean isPhoneValid(String phone) {
        if(phone==null) return false;
        return phone.matches(PHONE_REGEX);
    }

    public static boolean isEmailValid(String email){
        if(email==null) return false;
        return email.matches(EMAIL_REGEX);
    }

    private static boolean isPasswordValid(String password){
        if(password==null) return false;
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isEvaluationValid(byte evaluation){
        return evaluation <= 10 && evaluation >= 1;
    }


    private static boolean isStringLess(String title, int size) {
        if (title == null) return false;
        return title.length() < size;
    }
}