package com.googlecode.commandme.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines an action
 *
 * @author Dmitry Sidorenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Action {
    /**
     * The name of this action
     *
     * @return The name of this action
     */
    String name() default "";

    /**
     * A description of this action
     *
     * @return A description of this action
     */
    String description() default "";

    /**
     * is this is default action for module
     *
     * @return is this is default action for module
     */
    boolean defaultAction() default false;

    /**
     * Should help be displayed if this parameter is
     * present.
     *
     * @return True if this parameter is a help parameter
     */
    boolean helpRequest() default false;
}