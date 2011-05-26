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

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.OperandInvocationException;
import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.OperandDefinition;
import com.googlecode.commandme.impl.introspector.OptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Interrogates an instance, injects values of arguments and calls operands.
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
    private OptionDefinition optionDef = null;

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
     * Does actual injecting and calls operands
     */
    public void torture() {
        currentToken = TokenType.OPERAND;

        for (String argument : arguments) {
            LOGGER.debug("Parsing: {}", argument);
            //Previous token was option which expects value
            if (currentToken == TokenType.OPTION) {
                handleValue(argument);
            } else if (argument.startsWith("--")) {
                handleLongNameOption(argument.substring(2));
            } else if (argument.startsWith("-")) {
                handleMultipleOptions(argument.substring(1));
            } else {
                switch (currentToken) {
                    case OPTION:
                        handleValue(argument);
                        break;
                    case VALUE:
                    case OPERAND:
                    case OPTION_NO_VALUE:
                        handleOperand(argument);
                        break;
                    default:
                        LOGGER.warn("Something went wrong. Current token: '{}', previous tokenType: '{}'", new Object[]{argument, currentToken});
                        throw new CliException("Something went wrong");
                }
            }
        }
    }

    private void handleMultipleOptions(String argument) {
        for (Character c : argument.toCharArray()) {
            handleShortNameOption(c.toString());
        }
    }

    private void handleLongNameOption(String token) {
        optionDef = moduleIntrospector.getOptions().getByLongName(token);
        handleOption(token);
    }

    private void handleShortNameOption(String token) {
        optionDef = moduleIntrospector.getOptions().getByShortName(token);
        handleOption(token);
    }

    private void handleOption(String token) {
        LOGGER.debug("handle Option");
        if (optionDef == null) {
            throw new OptionSettingException("Can't find option:" + token);
        }
        currentToken = TokenType.OPTION;
        if (!optionDef.getInterrogator().needValue()) {
            currentToken = TokenType.OPTION_NO_VALUE;
            optionDef.getInterrogator().setValue(module, "no value");
            optionDef = null;
        }
    }

    private void handleOperand(String token) {
        LOGGER.debug("handle Operand");
        currentToken = TokenType.OPERAND;
        callOperand(token);
    }

    private void handleValue(String token) {
        LOGGER.debug("handle Value");
        currentToken = TokenType.VALUE;
        assert optionDef != null;
        optionDef.getInterrogator().setValue(module, token);
        // value parsed
        optionDef = null;
    }

    private void callOperand(String longOperandName) {
        OperandDefinition definition = moduleIntrospector.getOperands().getByLongName(longOperandName);
        if (definition != null) {
            LOGGER.debug("Executing operand: {}", definition);
            try {
                definition.getMethod().invoke(module);
            } catch (Exception e) {
                LOGGER.warn("Exception", e);
                throw new CliException("Exception invoking operand: " + definition, e);
            }
        } else {
            LOGGER.warn("Can't find operand: {}", longOperandName);
            throw new OperandInvocationException("Can't find operand: " + longOperandName);
        }
    }

    private enum TokenType {
        /**
         * Option with value
         */
        OPTION,
        /**
         * Option without value
         */
        OPTION_NO_VALUE,
        /**
         * Operand
         */
        OPERAND,
        /**
         * Value of a option
         */
        VALUE
    }
}
