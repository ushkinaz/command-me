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

import com.googlecode.commandme.impl.introspector.ModuleParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interrogates an instance, injects values of arguments and calls actions
 *
 * @author Dmitry Sidorenko
 */
public class Interrogator<T> {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(Interrogator.class);

    private T                module;
    private ModuleParameters moduleParameters;
    private String[]         arguments;

    /**
     * A constructor.
     *
     * @param instance         an instance to interrogate
     * @param moduleParameters definitions
     * @param arguments        actual arguments
     */
    Interrogator(T instance, ModuleParameters moduleParameters, String[] arguments) {
        this.module = instance;
        this.moduleParameters = moduleParameters;
        this.arguments = arguments;
    }

    /**
     * Does actual injecting and calls actions
     */
    public void torture() {
        for (String argument : arguments) {
            LOGGER.debug(argument);
            if (argument.startsWith("-")) {
                LOGGER.debug("Found parameter");
            } else {
                LOGGER.debug("Found action or value");
            }
        }
    }
}
