package by.epam.store.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Bean.
 * Target classes.
 * Generate object of Class and add it
 * in the app container.
 * @apiNote Class must contain constructor
 *
 * @see ApplicationContainer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Bean {
}
