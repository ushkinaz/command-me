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

import java.lang.reflect.InvocationTargetException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.impl.introspector.OptionDefinition;

/**
 * Special case for booleans. We don't need value for them.
 *
 * @author Dmitry Sidorenko
 */
public class BooleanPropertyVivisector implements PropertyVivisector {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(BooleanPropertyVivisector.class);
    private final OptionDefinition optionDefinition;

    BooleanPropertyVivisector(OptionDefinition optionDefinition) {
        this.optionDefinition = optionDefinition;
    }

    @Override
    public void vivisect(Object instance) {
        LOGGER.debug("Setting 'true' to {}", new Object[]{optionDefinition});

        try {
            optionDefinition.getWriterMethod().invoke(instance, true);
        } catch (IllegalAccessException e) {
            throw new OptionSettingException("Can't convert value from String '", e);
        } catch (InvocationTargetException e) {
            LOGGER.warn("Can't convert value from String", e);
            throw new OptionSettingException("Can't convert value from String '", e);
        }
    }

    @Override
    public void prepare(String... values) throws VivisectorException {
        // Value currently is not supported
    }

    @Override
    public boolean needValue() {
        return false;
    }
}
