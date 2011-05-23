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

import com.googlecode.commandme.ParameterSettingException;
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
    private final ParameterDefinition parameterDefinition;

    DefaultPropertyInterrogator(ParameterDefinition parameterDefinition) {
        this.parameterDefinition = parameterDefinition;
    }

    @Override
    public void setValue(Object instance, String value) {
        LOGGER.debug("Setting value '{}' to {}", new Object[]{value, parameterDefinition});
        Class parameterType = parameterDefinition.getType();
        if (parameterType.isPrimitive()) {
            parameterType = findWrapperClass(parameterType);
        }
        try {
            Constructor constructor = parameterType.getConstructor(String.class);
            Object convertedValue = constructor.newInstance(value);
            parameterDefinition.getWriterMethod().invoke(instance, convertedValue);
        } catch (NoSuchMethodException e) {
            LOGGER.warn("Can't find appropriate constructor for {}", parameterType, e);
            throw new ParameterSettingException("Can't find appropriate constructor for " + parameterType, e);
        } catch (InstantiationException e) {
            LOGGER.warn("Can't convert value from String '{}' to {}", new Object[]{value, parameterType}, e);
            throw new ParameterSettingException("Can't convert value from String '" + value + "' to " + parameterType, e);
        } catch (IllegalAccessException e) {
            LOGGER.warn("Can't convert value from String '{}' to {}", new Object[]{value, parameterType}, e);
            throw new ParameterSettingException("Can't convert value from String '" + value + "' to " + parameterType, e);
        } catch (InvocationTargetException e) {
            LOGGER.warn("Can't convert value from String '{}' to {}", new Object[]{value, parameterType}, e);
            throw new ParameterSettingException("Can't convert value from String '" + value + "' to " + parameterType, e);
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
            throw new ParameterSettingException("Can't find class for primitive type " + primitiveClass, e);
        }
    }
}
