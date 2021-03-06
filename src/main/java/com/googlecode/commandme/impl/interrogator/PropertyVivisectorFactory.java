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

package com.googlecode.commandme.impl.interrogator;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.commandme.OptionDefinitionException;
import com.googlecode.commandme.impl.introspector.OptionDefinition;

/**
 * @author Dmitry Sidorenko
 */
public class PropertyVivisectorFactory {
    @SuppressWarnings({"unused"})
    private static final Logger                    LOGGER               = LoggerFactory.getLogger(PropertyVivisectorFactory.class);
    private static       PropertyVivisectorFactory factory              = new PropertyVivisectorFactory();
    private static final Set<Class>                allowedOptionClasses = new HashSet<Class>();

    static {
        allowedOptionClasses.add(String.class);
        allowedOptionClasses.add(Integer.class);
        allowedOptionClasses.add(Long.class);
        allowedOptionClasses.add(Integer.class);
        allowedOptionClasses.add(Byte.class);
        allowedOptionClasses.add(Short.class);
        allowedOptionClasses.add(Double.class);
        allowedOptionClasses.add(Float.class);
        allowedOptionClasses.add(Boolean.class);
    }

    public static PropertyVivisector createInterrogator(OptionDefinition optionDefinition) {
        return factory.createInterrogatorInternal(optionDefinition);
    }

    public PropertyVivisector createInterrogatorInternal(OptionDefinition optionDefinition) {
        PropertyVivisector vivisector;
        // Boolean has to be first
        if (optionDefinition.getType().equals(Boolean.TYPE) || optionDefinition.getType().equals(Boolean.class)) {
            vivisector = new BooleanPropertyVivisector(optionDefinition);
        } else if (optionDefinition.getType().isPrimitive()) {
            vivisector = new DefaultPropertyVivisector(optionDefinition);
        } else if (allowedOptionClasses.contains(optionDefinition.getType())) {
            vivisector = new DefaultPropertyVivisector(optionDefinition);
        } else {
            //Check if we have public constructor with single String argument
            try {
                optionDefinition.getType().getConstructor(String.class);
                vivisector = new DefaultPropertyVivisector(optionDefinition);
            } catch (NoSuchMethodException e) {
                LOGGER.warn("Can't find public construction(Sring) for " + optionDefinition.getType(), e);
                throw new OptionDefinitionException("Can't find public construction(Sring) for " + optionDefinition.getType());
            }
        }

        return vivisector;
    }

    public static void setFactory(PropertyVivisectorFactory factory) {
        PropertyVivisectorFactory.factory = factory;
    }

    public static void resetFactory() {
        factory = new PropertyVivisectorFactory();
    }
}
