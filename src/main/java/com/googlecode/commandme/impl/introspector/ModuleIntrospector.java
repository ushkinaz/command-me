package com.googlecode.commandme.impl.introspector;

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
    private static final String SETTER_PREFIX = "set";

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
     * @throws com.googlecode.commandme.CliException
     *
     */
    public ModuleParameters inspect() throws CliException {
        ModuleParameters moduleParameters = new ModuleParameters();
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clz, Introspector.USE_ALL_BEANINFO);
        } catch (IntrospectionException e) {
            LOGGER.error("Error", e);
            throw new CliException(e);
        }
        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            ParameterDefinition parameterDefinition = inspectMethod(propertyDescriptor);
            moduleParameters.addParameter(parameterDefinition);
        }
        return moduleParameters;
    }

    private ParameterDefinition inspectMethod(PropertyDescriptor propertyDescriptor) throws CliException {
        Parameter parameter = getParameterAnnotation(propertyDescriptor);

        if (parameter == null) {
            return null;
        }
        ParameterDefinition parameterDefinition = new ParameterDefinition();
        parameterDefinition.setDefaultValue(parameter.defaultValue());
        parameterDefinition.setDescription(parameter.description());

        String propertyName = getPropertyName(propertyDescriptor.getName());

        String longName = parameter.longName();
        if ("".equals(longName)) {
            longName = propertyName;
        }
        parameterDefinition.setLongName(longName);

        String shortName = parameter.shortName();
        if ("".equals(shortName)) {
            shortName = propertyName.substring(0, 1);
        }
        parameterDefinition.setShortName(shortName);

        parameterDefinition.setShowInHelp(parameter.helpRequest());

        parameterDefinition.setType(propertyDescriptor.getPropertyType());

        return parameterDefinition;
    }

    private Parameter getParameterAnnotation(PropertyDescriptor propertyDescriptor) {
        Method readMethod = propertyDescriptor.getReadMethod();
        if (readMethod != null && readMethod.getAnnotation(Parameter.class) != null) {
            return readMethod.getAnnotation(Parameter.class);
        }

        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod != null && writeMethod.getAnnotation(Parameter.class) != null) {
            return writeMethod.getAnnotation(Parameter.class);
        }

        return null;
    }

    private String getPropertyName(String methodName) {
        String propertyName = methodName;
        if (methodName.startsWith(SETTER_PREFIX)) {
            propertyName = methodName.substring(SETTER_PREFIX.length());
        }
        return propertyName;
    }

    public Class getClz() {
        return clz;
    }
}
