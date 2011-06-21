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

package com.googlecode.commandme.impl.interrogator.tortures;

import com.googlecode.commandme.impl.interrogator.HelpPrintStrategy;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 */
public class TortureHelp<T> extends TortureInstrument<T> {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(TortureHelp.class);
    private final ModuleIntrospector<T> moduleIntrospector;

    TortureHelp(T module, ModuleIntrospector<T> moduleIntrospector) {
        super(module);
        this.moduleIntrospector = moduleIntrospector;
    }

    private DefaultHelpStrategy createHelpStrategy() {
        //TODO: add factory
        return new DefaultHelpStrategy(
                module.getClass().getSimpleName(),
                moduleIntrospector.getOptions().getOptionDefinitions(),
                moduleIntrospector.getActions().getActionDefinitions());
    }

    @Override
    public void torture() {
        HelpPrintStrategy helpPrintStrategy = createHelpStrategy();
        helpPrintStrategy.printHelp();
    }
}
