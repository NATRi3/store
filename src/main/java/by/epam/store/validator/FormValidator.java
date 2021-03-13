package by.epam.store.validator;

import by.epam.store.util.RequestParameterAndAttribute;

import java.util.Map;

public class FormValidator {
    private static final int MAX_FEEDBACK_SIZE = 200;
    private static final int MAX_TITLE_NEWS_SIZE = 70;
    private static final int MAX_NAME_SIZE = 45;
    private static final int MAX_INFO_SIZE = 1000;
    private static final String EMAIL_REGEX = "([\\w]+\\.)*[\\w]+@[\\w]+(\\.[\\w]+)*\\.[\\w]{2,6}";
    private static final String PASSWORD_REGEX = "(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,40}";
    private static final String PHONE_REGEX = "\\+375\\(\\d{2}\\)\\d{3}-\\d{2}-\\d{2}";
    private static final int MAX_ADDRESS_SIZE = 70;
    private static final int MAX_INFO_500_SIZE = 500;

    public static boolean isFormValid(Map<String, String> dataMap) {
        boolean valid = true;
        if (dataMap.containsKey(RequestParameterAndAttribute.NAME_COLLECTION)) {
            String title = dataMap.get(RequestParameterAndAttribute.NAME_COLLECTION);
            if (!isStringLess(title, MAX_NAME_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.NAME_COLLECTION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.INFO_COLLECTION)) {
            String title = dataMap.get(RequestParameterAndAttribute.INFO_COLLECTION);
            if (!isStringLess(title, MAX_INFO_500_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.INFO_COLLECTION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.NEWS_TITLE)) {
            String title = dataMap.get(RequestParameterAndAttribute.NEWS_TITLE);
            if (!isStringLess(title, MAX_TITLE_NEWS_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.NEWS_TITLE);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.ID_COLLECTION)) {
            String id = dataMap.get(RequestParameterAndAttribute.ID_COLLECTION);
            if (!NumberValidator.isLongValid(id)) {
                dataMap.remove(RequestParameterAndAttribute.ID_COLLECTION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.NEWS_INFO)) {
            String info = dataMap.get(RequestParameterAndAttribute.NEWS_INFO);
            if (!isStringLess(info, MAX_INFO_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.NEWS_INFO);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.INFO_PRODUCT)) {
            String info = dataMap.get(RequestParameterAndAttribute.INFO_PRODUCT);
            if (!isStringLess(info, MAX_INFO_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.INFO_PRODUCT);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.PASSWORD)) {
            String password = dataMap.get(RequestParameterAndAttribute.PASSWORD);
            if (!isPasswordValid(password)) {
                dataMap.remove(RequestParameterAndAttribute.PASSWORD);
                valid = false;
            }
            if (dataMap.containsKey(RequestParameterAndAttribute.REPEAT_PASSWORD)) {
                String repeatPassword = dataMap.get(RequestParameterAndAttribute.PASSWORD);
                if (!password.equals(repeatPassword)) {
                    dataMap.remove(RequestParameterAndAttribute.PASSWORD);
                    valid = false;
                }
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.EMAIL)) {
            String email = dataMap.get(RequestParameterAndAttribute.EMAIL);
            if (!isEmailValid(email)) {
                dataMap.remove(RequestParameterAndAttribute.EMAIL);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.NAME)) {
            String name = dataMap.get(RequestParameterAndAttribute.NAME);
            if (!isStringLess(name, MAX_NAME_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.NAME);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.NAME_PRODUCT)) {
            String name = dataMap.get(RequestParameterAndAttribute.NAME_PRODUCT);
            if (!isStringLess(name, MAX_NAME_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.NAME_PRODUCT);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.NAME)) {
            String name = dataMap.get(RequestParameterAndAttribute.NAME);
            if (!isStringLess(name, MAX_NAME_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.NAME);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.PRICE_PRODUCT)) {
            String price = dataMap.get(RequestParameterAndAttribute.PRICE_PRODUCT);
            if (!NumberValidator.isNumberValidForBigDecimal(price)) {
                dataMap.remove(RequestParameterAndAttribute.PRICE_PRODUCT);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.REPEAT_PASSWORD)) {
            String pass = dataMap.get(RequestParameterAndAttribute.REPEAT_PASSWORD);
            if (!isPasswordValid(pass)) {
                dataMap.remove(RequestParameterAndAttribute.REPEAT_PASSWORD);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.ID_PRODUCT)) {
            String idProduct = dataMap.get(RequestParameterAndAttribute.ID_PRODUCT);
            if (!NumberValidator.isLongValid(idProduct)) {
                dataMap.remove(RequestParameterAndAttribute.ID_PRODUCT);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.FEEDBACK)) {
            String feedback = dataMap.get(RequestParameterAndAttribute.FEEDBACK);
            if (!isStringLess(feedback, MAX_FEEDBACK_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.FEEDBACK);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.EVALUATION)) {
            String evaluation = dataMap.get(RequestParameterAndAttribute.EVALUATION);
            if (!NumberValidator.isLongValid(evaluation) ||
                    !(Integer.parseInt(evaluation) >= 1 && Integer.parseInt(evaluation) <= 5)) {
                dataMap.remove(RequestParameterAndAttribute.EVALUATION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.ID_COLLECTION)) {
            String idCollection = dataMap.get(RequestParameterAndAttribute.ID_COLLECTION);
            if (!NumberValidator.isLongValid(idCollection)) {
                dataMap.remove(RequestParameterAndAttribute.ID_COLLECTION);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.PHONE)) {
            String phone = dataMap.get(RequestParameterAndAttribute.PHONE);
            if (!isPhoneValid(phone)) {
                dataMap.remove(RequestParameterAndAttribute.PHONE);
                valid = false;
            }
        }
        if (dataMap.containsKey(RequestParameterAndAttribute.ADDRESS)) {
            String address = dataMap.get(RequestParameterAndAttribute.ADDRESS);
            if (!isStringLess(address, MAX_ADDRESS_SIZE)) {
                dataMap.remove(RequestParameterAndAttribute.ADDRESS);
                valid = false;
            }
        }
        return valid;

    }

    private static boolean isPhoneValid(String phone) {
        if (phone == null) return false;
        return phone.matches(PHONE_REGEX);
    }

    public static boolean isEmailValid(String email) {
        if (email == null) return false;
        return email.matches(EMAIL_REGEX);
    }

    private static boolean isPasswordValid(String password) {
        if (password == null) return false;
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isEvaluationValid(byte evaluation) {
        return evaluation <= 10 && evaluation >= 1;
    }


    private static boolean isStringLess(String title, int size) {
        if (title == null) return false;
        return title.length() < size;
    }
}