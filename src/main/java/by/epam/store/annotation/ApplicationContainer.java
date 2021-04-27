package by.epam.store.annotation;

import by.epam.store.controller.command.TypeCommand;
import by.epam.store.controller.command.TypeCommandAsync;
import by.epam.store.controller.command.TypeCommandInterface;
import by.epam.store.exception.InitializationException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * The type Dependency injector.
 */
public class ApplicationContainer implements AutoCloseable {
    private static final Logger log = LogManager.getLogger(ApplicationContainer.class);
    private static ApplicationContainer applicationContainer;
    private static final AtomicBoolean isInit = new AtomicBoolean(false);
    private final Set<Object> classObjectMap = new HashSet<>();

    private ApplicationContainer() {
        Reflections reflections = new Reflections("by.epam.store.model", new SubTypesScanner(false));
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        for (Class<?> c : classes) {
            if (!c.isInterface()) {
                if (c.isAnnotationPresent(Bean.class)) {
                    try {
                        Constructor<?> constructor = c.getConstructor();
                        constructor.setAccessible(true);
                        Object obj = constructor.newInstance();
                        addObject(obj);
                    } catch (Throwable e) {
                        System.out.println(c);
                        log.fatal(e);
                        throw new InitializationException(e);
                    }
                }
            }
        }
        autowiredObjectsCollection(classObjectMap);
        autowiredObjectsCollection(getListCommandsFromMassive(TypeCommand.values()));
        autowiredObjectsCollection(getListCommandsFromMassive(TypeCommandAsync.values()));
        log.info("Container generated: " + classObjectMap.toString());
    }

    /**
     * Get injector dependency injector.
     *
     * @return the dependency injector
     */
    public static ApplicationContainer getApplicationContainer() {
        if (!isInit.get()) {
            synchronized (ApplicationContainer.class) {
                isInit.set(true);
                applicationContainer = new ApplicationContainer();
            }
        }
        return applicationContainer;
    }

    /**
     * Add object to container by @Dependency Class.
     *
     * @param o the o
     * @throws IllegalArgumentException when Class not implement interface with annotation @Dependency.
     */
    public void addObject(Object o) {
        classObjectMap.add(o);
    }

    /**
     * Get object from container of parameter Class.
     *
     * @param <T> the type Class
     * @param c   the Class
     * @return the T object from container
     * @throws IllegalArgumentException when Class not annotated by Dependency or Class not found.
     */
    @SuppressWarnings("unchecked")
    public <T> T getObject(Class<T> c) {
        T result = null;
        for(Object object : classObjectMap){
            Class<?> objClass = object.getClass();
            if (c.equals(objClass)) {
                result = (T) object;
            }
            Class<?>[] implClasses = objClass.getInterfaces();
            for (Class<?> impl : implClasses){
                if(impl.equals(c)){
                    if(result==null){
                        result = (T) object;
                    } else {
                        throw new InitializationException("Two object with same implementation " + c.getName());
                    }
                }
            }
        }
        if(result==null){
            throw new InitializationException("Object not found " + c.getName());
        }
        return result;
    }

    @Override
    public void close(){
        for(Object o : classObjectMap){
            for(Class<?> objInterface : o.getClass().getInterfaces()){
                if(objInterface.equals(AutoCloseable.class)){
                    try {
                        ((AutoCloseable) o ).close();
                    } catch (Exception e) {
                        log.error(e);
                    }
                }
            }
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
        List<T> collection = new ArrayList<>();
        for (TypeCommandInterface<T> t : typeCommandInterface) {
            collection.add(t.getCommand());
        }
        return collection;
    }
}
