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
import com.googlecode.commandme.OptionDefinitionException;
import com.googlecode.commandme.annotations.Option;
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
public class OptionsIntrospector<T> implements ModuleOptions {
    private static final Logger LOGGER = LoggerFactory.getLogger(OptionsIntrospector.class);

    private static final String SETTER_PREFIX = "set";

    private final Class<T>                         clz;
    private final List<OptionDefinition>        optionDefinitions;
    private final Map<String, OptionDefinition> shortNamesMap;
    private final Map<String, OptionDefinition> longNamesMap;


    public OptionsIntrospector(Class<T> clz) {
        this.clz = clz;
        optionDefinitions = new LinkedList<OptionDefinition>();
        shortNamesMap = new HashMap<String, OptionDefinition>();
        longNamesMap = new HashMap<String, OptionDefinition>();
    }

    /**
     * Adds new option definition.
     *
     * @param optionDefinition option
     */
    public void addOption(OptionDefinition optionDefinition) {
        if (optionDefinition == null) {
            return;
        }
        optionDefinitions.add(optionDefinition);

        String shortName = optionDefinition.getShortName();

        if (shortName != null && shortName.length() > 0) {
            if (shortNamesMap.containsKey(shortName)) {
                LOGGER.error("Already have option with short name: '{}'", shortName);
                throw new OptionDefinitionException("Already have option with short name: " + shortName);
            }
            shortNamesMap.put(shortName, optionDefinition);
        }

        String longName = optionDefinition.getLongName();
        if (longNamesMap.containsKey(longName)) {
            LOGGER.error("Already have option with name: '{}'", longName);
            throw new OptionDefinitionException("Already have option with name: " + longName);
        }
        longNamesMap.put(longName, optionDefinition);
    }

    @Override
    public List<OptionDefinition> getOptionDefinitions() {
        return Collections.unmodifiableList(optionDefinitions);
    }

    @Override
    public OptionDefinition getByLongName(String name) {
        return longNamesMap.get(name);
    }

    @Override
    public OptionDefinition getByShortName(String name) {
        return shortNamesMap.get(name);
    }

    @Override
    public void inspect() {
        for (Method method : clz.getMethods()) {
            if (method.getAnnotation(Option.class) != null) {
                OptionDefinition optionDefinition = inspectProperty(method.getAnnotation(Option.class), method);
                addOption(optionDefinition);
            }
        }
        assignDefaultShortNames();
    }

    private void assignDefaultShortNames() {
        Set<String> shorties = new HashSet<String>();
        Map<String, OptionDefinition> uniqueShorties = new HashMap<String, OptionDefinition>();

        for (OptionDefinition optionDefinition : optionDefinitions) {
            if (optionDefinition.getShortName() != null) {
                continue;
            }
            String shortName = optionDefinition.getLongName().substring(0, 1);
            boolean wasNotPresent = shorties.add(shortName);
            if (wasNotPresent) {
                uniqueShorties.put(shortName, optionDefinition);
            } else {
                LOGGER.warn("Default short names overlap {}, {}", new Object[]{uniqueShorties.get(shortName), optionDefinition});
                uniqueShorties.remove(shortName);
            }
        }
        for (Map.Entry<String, OptionDefinition> entry : uniqueShorties.entrySet()) {
            entry.getValue().setShortName(entry.getKey());
            LOGGER.debug("Set short name for {}", entry.getValue());
        }
        shortNamesMap.putAll(uniqueShorties);
    }

    private OptionDefinition inspectProperty(Option option, Method writerMethod) throws CliException {
        sanityChecks(writerMethod);
        OptionDefinition optionDefinition = new OptionDefinition();
        optionDefinition.setWriterMethod(writerMethod);
        optionDefinition.setDescription(option.description());

        String propertyName = getPropertyName(option, writerMethod);

        String longName = option.longName();
        if ("".equals(longName)) {
            longName = propertyName;
        }
        optionDefinition.setLongName(longName);

        String shortName = option.shortName();
        if (shortName.length() > 1) {
            LOGGER.error("Short name can't be long: {}", option);
            throw new OptionDefinitionException("Short name can't be long: " + option);

        }
        if (shortName.length() > 0) {
            optionDefinition.setShortName(shortName);
        }

        optionDefinition.setShowInHelp(option.helpRequest());

        optionDefinition.setType(writerMethod.getParameterTypes()[0]);

        return optionDefinition;
    }

    private void sanityChecks(Method writerMethod) throws OptionDefinitionException {
        assert Modifier.isPublic(writerMethod.getModifiers()) : "Option setter must be public";

        if (writerMethod.getParameterTypes().length != 1) {
            throw new OptionDefinitionException("Option setter method must have only one argument: " + writerMethod);
        }
//        Class paramClass = writerMethod.getOptionTypes()[0];
//
//        if (!paramClass.isPrimitive()) {
//            throw new OptionDefinitionException("Option must be of primitive type: " + writerMethod);
//        }
//
    }

    /**
     * Returns property name as defined in java.
     *
     * @param option    annotation
     * @param writerMethod method with annotation
     * @return name of a property
     */
    private String getPropertyName(Option option, Method writerMethod) {
        String propertyName = option.longName();
        if (writerMethod.getName().startsWith(SETTER_PREFIX)) {
            propertyName = writerMethod.getName()
                    .substring(SETTER_PREFIX.length(), SETTER_PREFIX.length() + 1)
                    .toLowerCase() + writerMethod.getName().substring(SETTER_PREFIX.length() + 1);
        }
        return propertyName;
    }
}
