package com.googlecode.commandme.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines a parameter
 *
 * @author Dmitry Sidorenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Parameter {
    /**
     * The long name of this parameter
     *
     * @return The long name of this parameter
     */
    String longName() default "";

    /**
     * The short name of this parameter
     *
     * @return The short name of this parameter
     */
    String shortName() default "";

    /**
     * A description of this parameter
     *
     * @return A description of this parameter
     */
    String description() default "";

    /**
     * The default value if none is specified
     *
     * @return The value to present if none is specified
     */
    String defaultValue() default "";

    /**
     * Should help be displayed if this parameter is
     * present.
     *
     * @return True if this parameter is a help parameter
     */
    boolean helpRequest() default false;
}
