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
import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.impl.introspector.ActionDefinition;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.OptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.MessageFormat;

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
     * Does actual injecting and calls actions
     */
    public void torture() {
        currentToken = TokenType.ACTION;

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
                    case ACTION:
                    case OPTION_NO_VALUE:
                        handleAction(argument);
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

    /**
     * By this time, {@link #optionDef} should contain definition of an option.
     *
     * @param token token we are parsing
     */
    private void handleOption(String token) {
        LOGGER.debug("handle Option");
        if (optionDef == null) {
            if (handleHelpRequest(token)) {
                currentToken = TokenType.ACTION;
                return;
            }
            throw new OptionSettingException("Can't find option:" + token);
        }
        currentToken = TokenType.OPTION;
        if (!optionDef.getInterrogator().needValue()) {
            currentToken = TokenType.OPTION_NO_VALUE;
            optionDef.getInterrogator().setValue(module, "no value");
            optionDef = null;
        }
    }

    /**
     * @param token argument, w/o "--" or "-"
     * @return true if this is help request
     */
    private boolean handleHelpRequest(String token) {
        if (!"?".equals(token) && !"h".equals(token) && !"help".equals(token)) {
            return false;
        }

        System.out.println(module.getClass().getSimpleName());
        System.out.println("Usage: " + module.getClass().getSimpleName() + "[options] [actions]");

        System.out.println("\tOptions:");
        for (OptionDefinition optionDefinition : moduleIntrospector.getOptions().getOptionDefinitions()) {
            System.out
                    .println(MessageFormat.format("\t\t--{0}, -{1}: {2}", optionDefinition.getLongName(), optionDefinition
                            .getShortName(), optionDefinition.getDescription()));
        }

        System.out.println("\tActions:");
        for (ActionDefinition actionDefinition : moduleIntrospector.getActions().getActions()) {
            System.out
                    .println(MessageFormat.format("\t\t{0}, {1}: {2}", actionDefinition.getLongName(), actionDefinition.getShortName(), actionDefinition
                            .getDescription()));
        }


        return true;
    }

    private void handleAction(String token) {
        LOGGER.debug("handle Action");
        currentToken = TokenType.ACTION;
        callAction(token);
    }

    private void handleValue(String token) {
        LOGGER.debug("handle Value");
        currentToken = TokenType.VALUE;
        assert optionDef != null;
        optionDef.getInterrogator().setValue(module, token);
        // value parsed
        optionDef = null;
    }

    private void callAction(String longActionName) {
        ActionDefinition definition = moduleIntrospector.getActions().getByLongName(longActionName);
        if (definition != null) {
            LOGGER.debug("Executing action: {}", definition);
            try {
                definition.getMethod().invoke(module);
            } catch (Exception e) {
                LOGGER.warn("Exception", e);
                throw new CliException("Exception invoking action: " + definition, e);
            }
        } else {
            if (handleHelpRequest(longActionName)) {
                return;
            }
            LOGGER.warn("Can't find action: {}", longActionName);
            throw new ActionInvocationException("Can't find action: " + longActionName);
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
         * Action
         */
        ACTION,
        /**
         * Value of a option
         */
        VALUE
    }
}
