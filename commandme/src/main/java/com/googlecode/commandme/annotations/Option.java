package com.googlecode.commandme.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Dmitry Sidorenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Option {
    /**
     * The long name of this option
     *
     * @return The long name of this option
     */
    String longName() default "";

    /**
     * The short name of this option
     *
     * @return The short name of this option
     */
    String[] shortName() default "";

    /**
     * A description of this option
     *
     * @return A description of this option
     */
    String description() default "";

    /**
     * The default value if none is specified
     *
     * @return The value to present if none is specified
     */
    String[] defaultValue() default {};

    /**
     * Should help be displayed if this option is
     * present.
     *
     * @return True if this option is a help option
     */
    boolean helpRequest() default false;
}
