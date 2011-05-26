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

import java.lang.reflect.InvocationTargetException;

/**
 * Special case for booleans. We don't need value for them.
 *
 * @author Dmitry Sidorenko
 */
public class BooleanPropertyInterrogator implements PropertyInterrogator {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(BooleanPropertyInterrogator.class);
    private final OptionDefinition optionDefinition;

    BooleanPropertyInterrogator(OptionDefinition optionDefinition) {
        this.optionDefinition = optionDefinition;
    }

    @Override
    public void setValue(Object instance, String... values) {
        LOGGER.debug("Setting value '{}' to {}", new Object[]{values[0], optionDefinition});

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
    public boolean needValue() {
        return false;
    }
}
