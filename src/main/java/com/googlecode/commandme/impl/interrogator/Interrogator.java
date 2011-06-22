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

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.commandme.ActionInvocationException;
import com.googlecode.commandme.CliException;
import com.googlecode.commandme.impl.interrogator.tortures.TortureBuilder;
import com.googlecode.commandme.impl.interrogator.tortures.TortureInstrument;
import com.googlecode.commandme.impl.interrogator.tortures.TortureType;
import com.googlecode.commandme.impl.introspector.ActionDefinition;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.OptionDefinition;

/**
 * Interrogates an instance, injects values of arguments and calls actions.
 * Instances of this class are not reusable.
 *
 * @author Dmitry Sidorenko
 */
public class Interrogator<T> {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(Interrogator.class);

    private final String[]                arguments;
    private final T                       module;
    private final ModuleIntrospector<T>   moduleIntrospector;
    private       TortureBuilder<T>       tortureBuilder;
    private       TokenType               currentToken;
    private       OptionDefinition        optionDef;
    private       List<TortureInstrument> torturePlan;

    /**
     * A constructor.
     *
     * @param module             an instance to interrogate
     * @param moduleIntrospector initialized introspector
     * @param arguments          actual arguments
     */
    Interrogator(T module, ModuleIntrospector<T> moduleIntrospector, String[] arguments) {
        this.module = module;
        this.moduleIntrospector = moduleIntrospector;
        this.arguments = arguments;
        this.torturePlan = new LinkedList<TortureInstrument>();
    }

    /**
     * Does actual injecting and calls actions
     */
    public void interrogate() {
        try {
            prepareTorturesPlan();

            validateTorturesPlan();

            executeTorturesPlan();
        } catch (CliException e) {
            LOGGER.error("Trouble interrogating", e);
            tortureBuilder = TortureBuilder.getBuilder(module, moduleIntrospector);
            tortureBuilder.startBuilding(TortureType.HELP);
            tortureBuilder.finishTortureInstrument().torture();
            throw e;
        }
    }

    private void prepareTorturesPlan() {
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
                        LOGGER.warn("Something went wrong. Current token: '{}', previous tokenType: '{}'",
                                new Object[]{argument, currentToken});
                        throw new CliException("Something went wrong");
                }
            }
        }
    }

    private void validateTorturesPlan() {
        TorturesValidator<T> torturesValidator = new TorturesValidator<T>(this.torturePlan, moduleIntrospector.getOptions()
                .getOptionDefinitions());
        torturesValidator.validateTorturesPlan();
    }

    private void executeTorturesPlan() {
        for (TortureInstrument tortureInstrument : torturePlan) {
            tortureInstrument.torture();
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
            throw new VivisectorException("Can't find option:" + token);
        }

        currentToken = TokenType.OPTION;
        startBuildingTorture(TortureType.OPTION);
        tortureBuilder.addOption(optionDef);
        if (!optionDef.getVivisector().needValue()) {
            currentToken = TokenType.OPTION_NO_VALUE;
            finishBuildingTorture();
        }
    }

    private void handleValue(String token) {
        LOGGER.debug("handle Value");
        currentToken = TokenType.VALUE;
        assert optionDef != null;

        tortureBuilder.addValue(token);
        finishBuildingTorture();
    }

    /**
     * @param token argument, w/o "--" or "-"
     * @return true if this is help request
     */
    private boolean handleHelpRequest(String token) {
        if (!"?".equals(token) && !"h".equals(token) && !"help".equals(token)) {
            return false;
        }

        startBuildingTorture(TortureType.HELP);
        finishBuildingTorture();
        return true;
    }

    private void handleAction(String token) {
        LOGGER.debug("handle Action");
        currentToken = TokenType.ACTION;
        startBuildingTorture(TortureType.ACTION);

        ActionDefinition definition = moduleIntrospector.getActions().getByLongName(token);
        if (definition != null) {
            LOGGER.debug("Executing action: {}", definition);
            tortureBuilder.addAction(definition);
        } else {
            if (handleHelpRequest(token)) {
                return;
            }
            LOGGER.warn("Can't find action: {}", token);
            throw new ActionInvocationException("Can't find action: " + token);
        }
        finishBuildingTorture();
    }


    private void startBuildingTorture(TortureType tortureType) {
        tortureBuilder = TortureBuilder.getBuilder(module, moduleIntrospector);
        tortureBuilder.startBuilding(tortureType);
    }

    /**
     * Adds currently being built torture to the list of tortures, cleans references.
     */
    private void finishBuildingTorture() {
        torturePlan.add(tortureBuilder.finishTortureInstrument());
        optionDef = null;
        tortureBuilder = null;
    }
}
