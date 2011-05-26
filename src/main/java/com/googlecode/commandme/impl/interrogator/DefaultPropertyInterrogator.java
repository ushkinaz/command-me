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

import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.impl.introspector.OptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Default properties interrogator for primitive types
 *
 * @author Dmitry Sidorenko
 */
public class DefaultPropertyInterrogator implements PropertyInterrogator {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyInterrogator.class);
    private final OptionDefinition optionDefinition;

    DefaultPropertyInterrogator(OptionDefinition optionDefinition) {
        this.optionDefinition = optionDefinition;
    }

    @Override
    public void setValue(Object instance, String... values) {
        assert values.length == 1;

        LOGGER.debug("Setting value '{}' to {}", new Object[]{values[0], optionDefinition});
        Class optionType = optionDefinition.getType();
        if (optionType.isPrimitive()) {
            optionType = findWrapperClass(optionType);
        }
        try {
            Constructor constructor = optionType.getConstructor(String.class);
            Object convertedValue = constructor.newInstance(values[0]);
            optionDefinition.getWriterMethod().invoke(instance, convertedValue);
        } catch (NoSuchMethodException e) {
            LOGGER.warn("Can't find appropriate constructor for {}", optionType, e);
            throw new OptionSettingException("Can't find appropriate constructor for " + optionType, e);
        } catch (InstantiationException e) {
            LOGGER.warn("Can't convert value from String '{}' to {}", new Object[]{values, optionType}, e);
            throw new OptionSettingException("Can't convert value from String '" + values[0] + "' to " + optionType, e);
        } catch (IllegalAccessException e) {
            LOGGER.warn("Can't convert value from String '{}' to {}", new Object[]{values, optionType}, e);
            throw new OptionSettingException("Can't convert value from String '" + values[0] + "' to " + optionType, e);
        } catch (InvocationTargetException e) {
            LOGGER.warn("Can't convert value from String '{}' to {}", new Object[]{values, optionType}, e);
            throw new OptionSettingException("Can't convert value from String '" + values[0] + "' to " + optionType, e);
        }
    }

    @Override
    public boolean needValue() {
        return true;
    }

    protected Class findWrapperClass(Class primitiveClass) {
        assert primitiveClass.isPrimitive();

        try {
            if (primitiveClass.equals(Integer.TYPE)) {
                return Integer.class;
            }
            //Constructing wrapper class name from primitive class name
            return Class.forName("java.lang." + Character.toUpperCase(primitiveClass.getName()
                    .charAt(0)) + primitiveClass.getName().substring(1));
        } catch (ClassNotFoundException e) {
            LOGGER.warn("Can't find class for primitive type {}", primitiveClass, e);
            throw new OptionSettingException("Can't find class for primitive type " + primitiveClass, e);
        }
    }
}
