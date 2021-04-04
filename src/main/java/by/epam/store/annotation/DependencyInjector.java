package by.epam.store.annotation;

import by.epam.store.controller.command.TypeCommand;
import by.epam.store.controller.command.TypeCommandAsync;
import by.epam.store.controller.command.TypeCommandInterface;
import by.epam.store.exception.InitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * The type Dependency injector.
 */
public class DependencyInjector {
    private static final Logger log = LogManager.getLogger(DependencyInjector.class);
    private static DependencyInjector applicationContainer;
    private final Map<Class<?>, Object> classObjectMap = new HashMap<>();

    private DependencyInjector() {
        Reflections reflections = new Reflections("by.epam.store.model", new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        for (Class<?> c : classes) {
            if (!c.isInterface()) {
                if (c.isAnnotationPresent(Bean.class)) {
                    try {
                        Object obj = c.getConstructor().newInstance();
                        addObject(obj);
                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        log.fatal(e);
                        throw new InitializationException(e);
                    }
                }
            }
        }
        autowiredObjectsCollection(classObjectMap.values());
        autowiredObjectsCollection(getListCommandsFromMassive(TypeCommand.values()));
        autowiredObjectsCollection(getListCommandsFromMassive(TypeCommandAsync.values()));
        log.info("Container generated: " + classObjectMap.toString());
    }

    /**
     * Get injector dependency injector.
     *
     * @return the dependency injector
     */
    public static DependencyInjector getApplicationContainer() {
        if (applicationContainer == null) {
            synchronized (DependencyInjector.class) {
                applicationContainer = new DependencyInjector();
            }
        }
        return applicationContainer;
    }

    /**
     * Add object to container by @Dependency Class.
     *
     * @throws IllegalArgumentException when Class not implement
     * interface with annotation @Dependency.
     * @param o the o
     */
    public void addObject(Object o) {
        Class<?> objectClass = o.getClass();
        Class<?>[] interfaces = objectClass.getInterfaces();
        for (Class<?> c : interfaces) {
            if (c.isAnnotationPresent(Dependency.class)) {
                classObjectMap.put(c, o);
                return;
            }
        }
        throw new IllegalArgumentException("Illegal object " + o.getClass().getName());
    }

    /**
     * Get object from container of parameter Class.
     *
     * @throws IllegalArgumentException when Class not annotated by Dependency
     * or Class not found.
     * @param <T> the type Class
     * @param c   the Class
     * @return the T object from container
     */
    public <T> T getObject(Class<T> c) {
        if (c.isAnnotationPresent(Dependency.class) && classObjectMap.containsKey(c)) {
            return (T) classObjectMap.get(c);
        } else {
            throw new IllegalArgumentException("Illegal dependency " + c.getName());
        }
    }

    private <T> void autowiredObjectsCollection(Collection<T> ts) {
        try {
            for (T t : ts) {
                Class<?> tClass = t.getClass();
                for (Method method : tClass.getMethods()) {
                    if (method.isAnnotationPresent(Autowired.class)) {
                        if (method.trySetAccessible()) {
                            method.setAccessible(true);
                            Class<?>[] parameters = method.getParameterTypes();
                            method.invoke(t, getObject(parameters[0]));
                            method.setAccessible(false);
                        }
                    }
                }
                for (Field field : tClass.getFields()) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        if(tClass.getField(field.getName()).get(t)==null) {
                            field.setAccessible(true);
                            field.set(t, getObject(field.getType()));
                        }
                    }
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException | InvocationTargetException e) {
            log.error(e);
            throw new InitializationException(e);
        }
    }

    private <T> List<T> getListCommandsFromMassive(TypeCommandInterface<T>[] typeCommandInterface) {
        List<T> collection = new ArrayList<T>();
        for (TypeCommandInterface<T> t : typeCommandInterface) {
            collection.add(t.getCommand());
        }
        return collection;
    }
}
