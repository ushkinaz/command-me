package com.googlecode.commandme.impl.interrogator;

import com.googlecode.commandme.impl.CliParameter;
import com.googlecode.commandme.impl.introspector.ModuleParameters;

import java.util.List;

/**
 * Interrogates an instance, injects values of parameters and calls actions
 *
 * @author Dmitry Sidorenko
 */
public class Interrogator<T> {
    /**
     * A constructor.
     *
     * @param instance          an instance to interrogate
     * @param moduleParameters definitions
     * @param parameters        actual parameters
     */
    Interrogator(T instance, ModuleParameters moduleParameters, List<CliParameter> parameters) {
    }

    /**
     * Does actual injecting and calls actions
     */
    public void torture() {
    }
}
