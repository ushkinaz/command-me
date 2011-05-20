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

/**
 * Introspects module class and creates options definition
 *
 * @author Dmitry Sidorenko
 */
public class ModuleIntrospector<T> {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(ModuleIntrospector.class);

    private final Class<T>         clz;
    private final ModuleParameters parametersIntrospector;
    private final ModuleActions    actionsIntrospector;

    /**
     * Creates an introspector
     *
     * @param clz class to introspect
     */
    ModuleIntrospector(Class<T> clz) {
        this.clz = clz;
        actionsIntrospector = new ActionsIntrospector<T>(clz);
        parametersIntrospector = new ParametersIntrospector<T>(clz);
    }

    /**
     * Introspects a module
     */
    public void inspect() {
        actionsIntrospector.inspect();
        parametersIntrospector.inspect();
    }

    public Class<T> getClz() {
        return clz;
    }

    public ModuleParameters getParameters() {
        return parametersIntrospector;
    }

    public ModuleActions getActions() {
        return actionsIntrospector;
    }
}
