package com.googlecode.commandme.impl.interrogator;

import com.googlecode.commandme.impl.introspector.ModuleParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interrogates an instance, injects values of arguments and calls actions
 *
 * @author Dmitry Sidorenko
 */
public class Interrogator<T> {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(Interrogator.class);

    private T module;
    private ModuleParameters moduleParameters;
    private String[] arguments;

    /**
     * A constructor.
     *
     * @param instance         an instance to interrogate
     * @param moduleParameters definitions
     * @param arguments       actual arguments
     */
    Interrogator(T instance, ModuleParameters moduleParameters, String[] arguments) {
        this.module = instance;
        this.moduleParameters = moduleParameters;
        this.arguments = arguments;
    }

    /**
     * Does actual injecting and calls actions
     */
    public void torture() {
        for (int i = 0; i < arguments.length; i++){
            LOGGER.debug(arguments[i]);
        }
    }
}
