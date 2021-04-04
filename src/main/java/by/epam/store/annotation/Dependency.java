package by.epam.store.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Dependency.
 * Target Classes or Interfaces which serve like
 * keys in application container.
 *
 * @apiNote application container can't contain two
 * objects which implements one @Dependency Class.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Dependency {
}
