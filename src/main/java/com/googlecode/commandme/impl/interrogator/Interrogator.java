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

import com.googlecode.commandme.ActionInvocationException;
import com.googlecode.commandme.CliException;
import com.googlecode.commandme.impl.introspector.ActionDefinition;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.ParameterDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;

/**
 * Interrogates an instance, injects values of arguments and calls actions
 *
 * @author Dmitry Sidorenko
 */
public class Interrogator<T> {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(Interrogator.class);

    private final T                  module;
    private final ModuleIntrospector moduleIntrospector;
    private final String[]           arguments;

    /**
     * A constructor.
     *
     * @param module             an instance to interrogate
     * @param moduleIntrospector initialized introspector
     * @param arguments          actual arguments
     */
    Interrogator(T module, ModuleIntrospector moduleIntrospector, String[] arguments) {
        this.module = module;
        this.moduleIntrospector = moduleIntrospector;
        this.arguments = arguments;
    }

    /**
     * Does actual injecting and calls actions
     */
    public void torture() {
        TokenType currentToken = TokenType.ACTION;
        ParameterDefinition parameterDef = null;
        for (String argument : arguments) {
            LOGGER.debug("Parsing: " + argument);
            if (argument.startsWith("--")) {
                LOGGER.debug("Found parameter");
                currentToken = TokenType.PARAMETER;
                parameterDef = findParameterDefinition(argument.substring(2));
            } else {
                switch (currentToken) {
                    case PARAMETER:
                        LOGGER.debug("Found value");
                        currentToken = TokenType.VALUE;
                        setParameterValue(parameterDef, argument);
                        break;
                    case VALUE:
                    case ACTION:
                        LOGGER.debug("Found action");
                        currentToken = TokenType.ACTION;
                        callAction(argument);
                        break;
                }
            }
        }
    }

    private void setParameterValue(ParameterDefinition parameterDefinition, String value) {
        try {
            parameterDefinition.getWriterMethod().invoke(module, value);
        } catch (IllegalAccessException e) {
            throw new CliException(e);
        } catch (InvocationTargetException e) {
            throw new CliException(e);
        }
    }

    private ParameterDefinition findParameterDefinition(String name) {
        return moduleIntrospector.getParameters().getByLongName(name);
    }

    private void callAction(String longActionName) {
        ActionDefinition definition = moduleIntrospector.getActions().getByLongName(longActionName);
        if (definition != null) {
            LOGGER.debug("Executing action:" + definition);
            try {
                definition.getMethod().invoke(module);
            } catch (Exception e) {
                LOGGER.warn("Exception", e);
                throw new CliException("Exception invoking action " + definition, e);
            }
        } else {
            LOGGER.warn("Can't find action:" + longActionName);
            throw new ActionInvocationException("Can't find action:" + longActionName);
        }
    }

    private enum TokenType {
        /**
         * Parameter with value
         */
        PARAMETER,
        /**
         * Action
         */
        ACTION,
        /**
         * Value of a parameter
         */
        VALUE
    }
}
