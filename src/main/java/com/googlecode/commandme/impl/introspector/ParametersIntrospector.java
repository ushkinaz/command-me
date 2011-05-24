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

        String shortName = parameterDefinition.getShortName();

        if (shortName != null && shortName.length() > 0) {
            if (shortNamesMap.containsKey(shortName)) {
                LOGGER.error("Already have parameter with short name: '{}'", shortName);
                throw new ParameterDefinitionException("Already have parameter with short name: " + shortName);
            }
            shortNamesMap.put(shortName, parameterDefinition);
        }

        String longName = parameterDefinition.getLongName();
        if (longNamesMap.containsKey(longName)) {
            LOGGER.error("Already have parameter with name: '{}'", longName);
            throw new ParameterDefinitionException("Already have parameter with name: " + longName);
        }
        longNamesMap.put(longName, parameterDefinition);
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
        assignDefaultShortNames();
    }

    private void assignDefaultShortNames() {
        Set<String> shorties = new HashSet<String>();
        Map<String, ParameterDefinition> uniqueShorties = new HashMap<String, ParameterDefinition>();

        for (ParameterDefinition parameterDefinition : parameterDefinitions) {
            if (parameterDefinition.getShortName() != null) {
                continue;
            }
            String shortName = parameterDefinition.getLongName().substring(0, 1);
            boolean wasNotPresent = shorties.add(shortName);
            if (wasNotPresent) {
                uniqueShorties.put(shortName, parameterDefinition);
            } else {
                LOGGER.warn("Default short names overlap {}, {}", new Object[]{uniqueShorties.get(shortName), parameterDefinition});
                uniqueShorties.remove(shortName);
            }
        }
        for (Map.Entry<String, ParameterDefinition> entry : uniqueShorties.entrySet()) {
            entry.getValue().setShortName(entry.getKey());
            LOGGER.debug("Set short name for {}", entry.getValue());
        }
        shortNamesMap.putAll(uniqueShorties);
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
        if (shortName.length() > 1) {
            LOGGER.error("Short name can't be long: {}", parameter);
            throw new ParameterDefinitionException("Short name can't be long: " + parameter);

        }
        if (shortName.length() > 0) {
            parameterDefinition.setShortName(shortName);
        }

        parameterDefinition.setShowInHelp(parameter.helpRequest());

        parameterDefinition.setType(writerMethod.getParameterTypes()[0]);

        return parameterDefinition;
    }

    private void sanityChecks(Method writerMethod) throws ParameterDefinitionException {
        assert Modifier.isPublic(writerMethod.getModifiers()) : "Parameter setter must be public";

        if (writerMethod.getParameterTypes().length != 1) {
            throw new ParameterDefinitionException("Parameter setter method must have only one argument: " + writerMethod);
        }
//        Class paramClass = writerMethod.getParameterTypes()[0];
//
//        if (!paramClass.isPrimitive()) {
//            throw new ParameterDefinitionException("Parameter must be of primitive type: " + writerMethod);
//        }
//
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
