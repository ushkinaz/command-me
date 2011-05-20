/*
 * Copyright (c) 2010-2011, Dmitry Sidorenko. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import java.util.*;

/**
 * Definition of options.
 * Created from annotated module class.
 *
 * @author Dmitry Sidorenko
 */
public class ParametersIntrospector<T> implements ModuleParameters {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParametersIntrospector.class);

    private static final String SETTER_PREFIX = "set";

    private final Class<T>                         clz;
    private final List<ParameterDefinition>        parameterDefinitions;
    private final Map<String, ParameterDefinition> shortNamesMap;
    private final Map<String, ParameterDefinition> longNamesMap;


    public ParametersIntrospector(Class<T> clz) {
        this.clz = clz;
        parameterDefinitions = new LinkedList<ParameterDefinition>();
        shortNamesMap = new HashMap<String, ParameterDefinition>();
        longNamesMap = new HashMap<String, ParameterDefinition>();
    }

    /**
     * Adds new parameter definition.
     *
     * @param parameterDefinition parameter
     */
    public void addParameter(ParameterDefinition parameterDefinition) {
        if (parameterDefinition == null) {
            return;
        }
        parameterDefinitions.add(parameterDefinition);
        shortNamesMap.put(parameterDefinition.getShortName(), parameterDefinition);
        longNamesMap.put(parameterDefinition.getLongName(), parameterDefinition);
    }

    @Override
    public List<ParameterDefinition> getParameterDefinitions() {
        return Collections.unmodifiableList(parameterDefinitions);
    }

    @Override
    public ParameterDefinition getByLongName(String name) {
        return longNamesMap.get(name);
    }

    @Override
    public ParameterDefinition getByShortName(String name) {
        return shortNamesMap.get(name);
    }

    /**
     * Inspects class for parameters
     */
    public void inspect() {
        BeanInfo beanInfo;
        try {
            beanInfo = Introspector.getBeanInfo(clz, Introspector.USE_ALL_BEANINFO);
        } catch (IntrospectionException e) {
            LOGGER.error("Error", e);
            throw new CliException(e);
        }
        for (PropertyDescriptor propertyDescriptor : beanInfo.getPropertyDescriptors()) {
            ParameterDefinition parameterDefinition = inspectMethod(propertyDescriptor);
            addParameter(parameterDefinition);
        }
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
}
