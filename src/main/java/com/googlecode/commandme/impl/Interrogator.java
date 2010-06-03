package com.googlecode.commandme.impl;

import java.util.List;

/**
 * Interrogates an instance, injects values of parameters and calls actions
 *
 * @author Dmitry Sidorenko
 */
class Interrogator {
    /**
     * A constructor.
     *
     * @param instance          an instance to interrogate
     * @param optionsDefinition definitions
     * @param parameters        actual parameters
     */
    public <T> Interrogator(T instance, OptionsDefinition optionsDefinition, List<CliParameter> parameters) {
    }

    /**
     * Does actual injecting and calls actions
     */
    public void torture() {
    }
}
