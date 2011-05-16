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

import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
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

    private T                  module;
    private ModuleIntrospector moduleIntrospector;
    private String[]           arguments;

    /**
     * A constructor.
     *
     * @param instance           an instance to interrogate
     * @param moduleIntrospector
     * @param arguments          actual arguments
     */
    Interrogator(T instance, ModuleIntrospector moduleIntrospector, String[] arguments) {
        this.module = instance;
        this.moduleIntrospector = moduleIntrospector;
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
