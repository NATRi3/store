package by.epam.store.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Autowired.
 * Target field and setters with parameter Interfaces which
 * must annotate by Dependency annotation. Inject object
 * to the generated Class object on compilation state.
 *
 * @see by.epam.store.annotation.Dependency
 * @see by.epam.store.annotation.DependencyInjector
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface Autowired {
}
