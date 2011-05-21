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
import com.googlecode.commandme.ParameterDefinitionException;
import com.googlecode.commandme.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
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

    private static final Set<Class> allowedParameterClasses = new HashSet<Class>();

    static {
        allowedParameterClasses.add(String.class);
        allowedParameterClasses.add(Integer.class);
        allowedParameterClasses.add(Long.class);
        allowedParameterClasses.add(Integer.class);
        allowedParameterClasses.add(Byte.class);
        allowedParameterClasses.add(Short.class);
        allowedParameterClasses.add(Double.class);
        allowedParameterClasses.add(Float.class);
        allowedParameterClasses.add(Boolean.class);
    }

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

    @Override
    public void inspect() {
        for (Method method : clz.getMethods()) {
            if (method.getAnnotation(Parameter.class) != null) {
                ParameterDefinition parameterDefinition = inspectProperty(method.getAnnotation(Parameter.class), method);
                addParameter(parameterDefinition);
            }
        }
    }

    private ParameterDefinition inspectProperty(Parameter parameter, Method writerMethod) throws CliException {
        sanityChecks(writerMethod);
        ParameterDefinition parameterDefinition = new ParameterDefinition();
        parameterDefinition.setWriterMethod(writerMethod);
        parameterDefinition.setDefaultValue(parameter.defaultValue());
        parameterDefinition.setDescription(parameter.description());

        String propertyName = getPropertyName(parameter, writerMethod);

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

        parameterDefinition.setType(writerMethod.getParameterTypes()[0]);

        return parameterDefinition;
    }

    private void sanityChecks(Method writerMethod) throws ParameterDefinitionException {
        assert Modifier.isPublic(writerMethod.getModifiers()) : "Parameter setter must be public";

        if (writerMethod.getParameterTypes().length != 1) {
            throw new ParameterDefinitionException("Parameter setter method must have only one argument: " + writerMethod);
        }
        Class paramClass = writerMethod.getParameterTypes()[0];

        if (!(paramClass.isPrimitive() || allowedParameterClasses.contains(paramClass))) {
            throw new ParameterDefinitionException("Parameter must be of primitive type: " + writerMethod);
        }

    }

    /**
     * Returns property name as defined in java.
     *
     * @param parameter    annotation
     * @param writerMethod method with annotation
     * @return name of a property
     */
    private String getPropertyName(Parameter parameter, Method writerMethod) {
        String propertyName = parameter.longName();
        if (writerMethod.getName().startsWith(SETTER_PREFIX)) {
            propertyName = writerMethod.getName()
                    .substring(SETTER_PREFIX.length(), SETTER_PREFIX.length() + 1)
                    .toLowerCase() + writerMethod.getName().substring(SETTER_PREFIX.length() + 1);
        }
        return propertyName;
    }
}
