package com.googlecode.commandme.impl.introspector;

public interface PropertyInterrogator {
    /**
     * Sets value from it's string representation
     *
     * @param instance
     * @param parameterDefinition
     * @param value
     */
    void setValue(Object instance, ParameterDefinition parameterDefinition, String value);

    /**
     * Does this property type needs value to be set?
     * Common use case is boolean parameter.
     *
     * @return true if can be avoided
     */
    boolean needValue();
}
