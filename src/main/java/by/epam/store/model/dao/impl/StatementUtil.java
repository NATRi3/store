package by.epam.store.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

public class StatementUtil {
    public static final Logger log = LogManager.getLogger(StatementUtil.class);
    public static final Map<Class<?>, Method> STATEMENT_METHOD_MAP = new HashMap<>();
    public static Map<Class<?>, Method> WRAPPER_AND_PRIMITIVE;

    static {
        try {
            WRAPPER_AND_PRIMITIVE = Map.of(
                    Integer.class, Number.class.getMethod("intValue"),
                    Byte.class, Number.class.getMethod("byteValue"),
                    Short.class, Number.class.getMethod("shortValue"),
                    Long.class, Number.class.getMethod("longValue"),
                    Double.class, Number.class.getMethod("doubleValue"),
                    Float.class, Number.class.getMethod("floatValue")
            );
        } catch (NoSuchMethodException e) {
            log.error(e);
        }
        for (Method method : PreparedStatement.class.getMethods()) {
            Class<?>[] paramsClass = method.getParameterTypes();
            if (paramsClass.length == 2 && paramsClass[0].equals(int.class)) {
                Class<?> classParam = paramsClass[1];
                if (classParam.isPrimitive()) {
                    if (int.class.equals(classParam)) {
                        classParam = Integer.class;
                    } else if (long.class.equals(classParam)) {
                        classParam = Long.class;
                    } else if (byte.class.equals(classParam)) {
                        classParam = Byte.class;
                    } else if (boolean.class.equals(classParam)) {
                        classParam = Boolean.class;
                    } else if (short.class.equals(classParam)) {
                        classParam = Short.class;
                    } else if (char.class.equals(classParam)) {
                        classParam = Character.class;
                    } else if (float.class.equals(classParam)) {
                        classParam = Float.class;
                    } else if (double.class.equals(classParam)) {
                        classParam = Double.class;
                    }
                }
                STATEMENT_METHOD_MAP.put(classParam, method);
            }
        }
    }

    static void setStatementParameters(PreparedStatement statement, Object... objects) {
        int i = 0;
        for (Object object : objects) {
            Class<?> objectClass = object.getClass();
            Method castMethod = null;
            if (WRAPPER_AND_PRIMITIVE.containsKey(objectClass)) {
                castMethod = WRAPPER_AND_PRIMITIVE.get(objectClass);
                castMethod.setAccessible(true);
            }
            Method method = STATEMENT_METHOD_MAP.get(objectClass);
            if (method != null) {
                method.setAccessible(true);
                try {
                    if (castMethod != null) {
                        method.invoke(statement, ++i, castMethod.invoke(object));
                    } else {
                        method.invoke(statement, ++i, object);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                throw new IllegalArgumentException("Illegal argument in state " + object.getClass().getName());
            }
        }
    }
}
