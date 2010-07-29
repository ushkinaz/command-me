package com.googlecode.commandme.impl.interrogator;

import com.googlecode.commandme.impl.introspector.ModuleParameters;

/**
 * Interrogates an instance, injects values of parameters and calls actions
 *
 * @author Dmitry Sidorenko
 */
public class Interrogator<T> {
    /**
     * A constructor.
     *
     * @param instance         an instance to interrogate
     * @param moduleParameters definitions
     * @param parameters       actual parameters
     */
    Interrogator(Object instance, ModuleParameters moduleParameters, String[] parameters) {
    }

    /**
     * Does actual injecting and calls actions
     */
    public void torture() {
    }
}
