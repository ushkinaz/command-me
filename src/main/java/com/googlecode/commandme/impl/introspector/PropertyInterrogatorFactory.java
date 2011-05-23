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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Dmitry Sidorenko
 */
public class PropertyInterrogatorFactory {
    @SuppressWarnings({"unused"})
    private static final Logger                      LOGGER                  = LoggerFactory.getLogger(PropertyInterrogatorFactory.class);
    private static       PropertyInterrogatorFactory factory                 = new PropertyInterrogatorFactory();
    private static final Set<Class>                  allowedParameterClasses = new HashSet<Class>();

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

    public static PropertyInterrogator createInterrogator(ParameterDefinition parameterDefinition) {
        return factory.createInterrogatorInternal(parameterDefinition);
    }

    public PropertyInterrogator createInterrogatorInternal(ParameterDefinition parameterDefinition) {
        if (allowedParameterClasses.contains(parameterDefinition.getType())) {
            return new DefaultPropertyInterrogator(parameterDefinition);
        } else {
            return new DefaultPropertyInterrogator(parameterDefinition);
        }
    }

    public static void setFactory(PropertyInterrogatorFactory factory) {
        PropertyInterrogatorFactory.factory = factory;
    }

    public static void resetFactory() {
        factory = new PropertyInterrogatorFactory();
    }
}
