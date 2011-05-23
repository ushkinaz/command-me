package com.googlecode.commandme.impl.introspector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 */
public class DefaultPropertyInterrogator implements PropertyInterrogator {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyInterrogator.class);

    public DefaultPropertyInterrogator(Class type) {
        //TODO: Implement com.googlecode.commandme.impl.introspector.DefaultPropertyInterrogator#DefaultPropertyInterrogator

    }

    @Override
    public void setValue(Object instance, ParameterDefinition parameterDefinition, String value) {
        //TODO: Implement com.googlecode.commandme.impl.introspector.DefaultPropertyInterrogator#setValue

    }

    @Override
    public boolean needValue() {
        //TODO: Implement com.googlecode.commandme.impl.introspector.DefaultPropertyInterrogator#needValue
        return false;
    }
}
