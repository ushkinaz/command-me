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

import com.googlecode.commandme.OperandInvocationException;
import com.googlecode.commandme.CliException;
import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.impl.introspector.OperandDefinition;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.OptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interrogates an instance, injects values of arguments and calls actions.
 * Instances of this class are not reusable.
 *
 * @author Dmitry Sidorenko
 */
public class Interrogator<T> {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(Interrogator.class);

    private final T                  module;
    private final ModuleIntrospector moduleIntrospector;
    private final String[]           arguments;
    private       TokenType          currentToken;
    private       boolean            expectsValue;
    private OptionDefinition parameterDef = null;

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
        currentToken = TokenType.ACTION;
        expectsValue = false;

        for (String argument : arguments) {
            LOGGER.debug("Parsing: {}", argument);
            //Previous token was parameter which expects value
            if (expectsValue) {
                handleValue(argument);
            } else if (argument.startsWith("--")) {
                handleParameter(argument.substring(2), false);
            } else if (argument.startsWith("-")) {
                handleParameter(argument.substring(1), true);
            } else {
                switch (currentToken) {
                    case PARAMETER:
                        handleValue(argument);
                        break;
                    case VALUE:
                    case ACTION:
                        handleAction(argument);
                        break;
                }
            }
        }
    }

    private void handleParameter(String token, boolean shortForm) {
        LOGGER.debug("handleParameter");
        currentToken = TokenType.PARAMETER;
        if (shortForm) {
            parameterDef = moduleIntrospector.getParameters().getByShortName(token);
        } else {
            parameterDef = moduleIntrospector.getParameters().getByLongName(token);
        }

        if (parameterDef == null) {
            throw new OptionSettingException("Can't find parameter:" + token);
        }
        expectsValue = parameterDef.getInterrogator().needValue();
    }

    private void handleAction(String token) {
        LOGGER.debug("handleAction");
        currentToken = TokenType.ACTION;
        callAction(token);
    }

    private void handleValue(String token) {
        LOGGER.debug("handleValue");
        currentToken = TokenType.VALUE;
        assert parameterDef != null;
        parameterDef.getInterrogator().setValue(module, token);
        // value parsed
        parameterDef = null;
        expectsValue = false;
    }

    private void callAction(String longActionName) {
        OperandDefinition definition = moduleIntrospector.getActions().getByLongName(longActionName);
        if (definition != null) {
            LOGGER.debug("Executing action: {}", definition);
            try {
                definition.getMethod().invoke(module);
            } catch (Exception e) {
                LOGGER.warn("Exception", e);
                throw new CliException("Exception invoking action: " + definition, e);
            }
        } else {
            LOGGER.warn("Can't find action: {}", longActionName);
            throw new OperandInvocationException("Can't find action: " + longActionName);
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
