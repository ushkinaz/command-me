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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.commandme.impl.introspector.ModuleIntrospector;

/**
 * Responsible for creating interrogators
 *
 * @author Dmitry Sidorenko
 */
public class InterrogatorFactory {
    private static final Logger LOGGER = LoggerFactory.getLogger(InterrogatorFactory.class);

    private static InterrogatorFactory factory;

    static {
        factory = new InterrogatorFactory();
    }

    /**
     * Creates interrogator
     *
     * @param instance           module instance
     * @param moduleIntrospector module introspector
     * @param options            options passed from command line
     * @return created interrogator
     */
    public static <T> Interrogator<T> createInterrogator(T instance, ModuleIntrospector moduleIntrospector, String[] options) {
        return factory.create(instance, moduleIntrospector, options);
    }

    public static void setFactory(InterrogatorFactory factory) {
        InterrogatorFactory.factory = factory;
    }

    public static void resetFactory() {
        InterrogatorFactory.factory = new InterrogatorFactory();
    }

    protected <T> Interrogator<T> create(T instance, ModuleIntrospector moduleIntrospector, String[] options) {
        LOGGER.debug("Creating interrogator for {} ", moduleIntrospector.getClz().getName());
        return new Interrogator<T>(instance, moduleIntrospector, options);
    }

}
