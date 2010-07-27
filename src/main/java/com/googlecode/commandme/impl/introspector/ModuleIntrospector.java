package com.googlecode.commandme.impl.introspector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Introspects module class and creates options definition
 *
 * @author Dmitry Sidorenko
 */
public class ModuleIntrospector {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleIntrospector.class);

    private Class clz;

    /**
     * Creates an introspector
     *
     * @param clz class to introspect
     */
    ModuleIntrospector(Class clz) {
        this.clz = clz;
    }

    /**
     * Introspects a module
     *
     * @return definition of a module. Never {@code null}
     */
    public ModuleParameters inspect() {
        ModuleParameters moduleParameters = new ModuleParameters();
        for (Method method : clz.getMethods()) {
            ParameterDefinition parameterDefinition = inspectMethod(method);
            moduleParameters.addParameter(parameterDefinition);
        }
        return moduleParameters;
    }

    private ParameterDefinition inspectMethod(Method method) {
        //TODO implement
        return null;
    }

    public Class getClz() {
        return clz;
    }
}
