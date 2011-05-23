package com.googlecode.commandme.impl.introspector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 */
public class PropertyInterrogatorFactory {
    @SuppressWarnings({"unused"})
    private static final Logger                      LOGGER  = LoggerFactory.getLogger(PropertyInterrogatorFactory.class);
    private static       PropertyInterrogatorFactory factory = new PropertyInterrogatorFactory();

    public static PropertyInterrogator createInterrogator(Class type) {
        return factory.createInterrogatorInternal(type);
    }

    public PropertyInterrogator createInterrogatorInternal(Class type) {
        return new DefaultPropertyInterrogator(type);
    }

    public static void setFactory(PropertyInterrogatorFactory factory) {
        PropertyInterrogatorFactory.factory = factory;
    }

    public static void resetFactory() {
        factory = new PropertyInterrogatorFactory();
    }
}
