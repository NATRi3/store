package by.epam.store.model.dao.impl;

/**
 * The type Data base column.
 */
public class DataBaseColumn {
    /**
     *  ACCOUNT table columns.
     */
    public static final String ID_ACCOUNT = "id_accounts";
    public static final String ACCOUNT_EMAIL = "email";
    public static final String ACCOUNT_IMAGE = "image";
    public static final String ACCOUNT_REGISTER_DATE = "register_date";
    public static final String ACCOUNT_NAME = "name";
    public static final String ACCOUNT_ACCESS = "access";
    public static final String ACCOUNT_ROLE = "role";
    /**
     *  FEEDBACKS table columns.
     */
    public static final String ID_FEEDBACK = "id_feedback";
    public static final String FEEDBACK_EVALUATION = "evaluation";
    public static final String FEEDBACK_ID_PRODUCT = "id_product";
    public static final String FEEDBACK_ID_ACCOUNT = "id_account";
    public static final String FEEDBACK = "feedback";
    /**
     *  ORDERS table columns.
     */
    public static final String ORDER_PRODUCT_PRODUCT_AMOUNT = "product_amount";
    public static final String ID_ORDER = "id_orders";
    public static final String ORDER_PRICE = "price";
    public static final String ORDER_ADDRESS = "address";
    public static final String ORDER_PHONE = "phone";
    /**
     *  PRODUCTS table columns.
     */
    public static final String ID_PRODUCT = "id_products";
    public static final String PRODUCT_NAME = "name";
    public static final String PRODUCT_INFO = "info";
    public static final String STATUS = "status";
    public static final String PRODUCT_PRICE = "price";
    public static final String PRODUCT_IMAGE = "image";
    public static final String PRODUCT_ID_COLLECTION = "id_collection";
    /**
     *  NEWS table columns.
     */
    public static final String ID_NEWS = "id_news";
    public static final String NEWS_TITLE = "title";
    public static final String NEWS_INFO = "info";
    public static final String DATE = "date";
    public static final String NEWS_IMAGE = "image";
    /**
     * COLLECTION table columns.
     */
    public static final String ID_COLLECTION = "id_collection";
    public static final String COLLECTION_INFO = "info";
    public static final String COLLECTION_NAME = "name";

    private DataBaseColumn() {
    }
}
    /*int i = 0;
        for (Object object : objects) {
                Class<?> objectClass = object.getClass();
        if(WRAPPER_AND_PRIMITIVE.containsKey(objectClass)){
        Method method = WRAPPER_AND_PRIMITIVE.get(objectClass);
        method.setAccessible(true);
        }
        Method method = STATEMENT_METHOD_MAP.get();
        if (method != null) {
        method.setAccessible(true);
        try {
        method.invoke(statement, ++i, classParam.cast(object));
        } catch (IllegalAccessException | InvocationTargetException e) {
        throw new RuntimeException(e);
        }
        } else {
        throw new IllegalArgumentException("Illegal argument in state " + object.getClass().getName());
        }
        }
        System.out.println(statement.toString());

        try {
            WRAPPER_AND_PRIMITIVE = Map.of(
                    Integer.class, Number.class.getMethod("intValue"),
                    Byte.class,  Number.class.getMethod("byteValue"),
                    Short.class,  Number.class.getMethod("shortValue"),
                    Long.class,  Number.class.getMethod("longValue"),
                    Double.class,  Number.class.getMethod("doubleValue"),
                    Float.class,  Number.class.getMethod("floatValue")
            );
        } catch (NoSuchMethodException e) {
            log.error(e);
            throw new InitializationException(e);
        }
        for (Method method : PreparedStatement.class.getMethods()) {
            Class<?>[] paramsClass = method.getParameterTypes();
            if (paramsClass.length == 2 && paramsClass[0].equals(int.class)) {
                Class<?> classParam = paramsClass[1];
                STATEMENT_METHOD_MAP.put(classParam, method);
            }
        }*/
