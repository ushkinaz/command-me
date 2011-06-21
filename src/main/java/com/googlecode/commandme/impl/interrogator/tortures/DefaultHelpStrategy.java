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

import java.text.MessageFormat;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.commandme.impl.introspector.ActionDefinition;
import com.googlecode.commandme.impl.introspector.OptionDefinition;

class DefaultHelpStrategy implements HelpPrintStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultHelpStrategy.class);


    private String                       applicationName;
    private Collection<OptionDefinition> optionDefinitions;
    private Collection<ActionDefinition> actionDefinitions;
    private StringBuilder helpBuilder = new StringBuilder(100);

    public DefaultHelpStrategy(String applicationName, Collection<OptionDefinition> optionDefinitions, Collection<ActionDefinition> actionDefinitions) {
        this.applicationName = applicationName;
        this.optionDefinitions = optionDefinitions;
        this.actionDefinitions = actionDefinitions;
    }

    @Override
    public void printHelp() {
        StringBuilder helpBuilder = this.helpBuilder;
        helpBuilder.append(applicationName).append("\n");
        helpBuilder.append(MessageFormat.format("Usage: {0} [options] [actions]\n", applicationName));

        appendAtLevel("Options:\n", 1);
        for (OptionDefinition optionDefinition : optionDefinitions) {
            appendAtLevel(buildOptionHelp(optionDefinition), 2);

        }

        appendAtLevel("Actions:\n", 1);
        for (ActionDefinition actionDefinition : actionDefinitions) {
            appendAtLevel(buildActionHelp(actionDefinition), 2);
        }
        System.out.println(helpBuilder);
    }

    private void appendAtLevel(String line, int level) {
        for (; level > 0; level--) {
            helpBuilder.append("\t");
        }
        helpBuilder.append(line);
    }

    private String buildActionHelp(ActionDefinition actionDefinition) {
        String helpString;
        if (actionDefinition.getShortName() == null) {
            helpString = MessageFormat.format("{0}: {1}\n",
                    actionDefinition.getLongName(),
                    wrapNull(actionDefinition.getDescription()));
        } else {
            helpString = MessageFormat.format("{0}, {1}: {2}\n",
                    actionDefinition.getShortName(),
                    actionDefinition.getLongName(),
                    wrapNull(actionDefinition.getDescription()));
        }
        return helpString;
    }

    private String buildOptionHelp(OptionDefinition optionDefinition) {
        String helpString;
        if (optionDefinition.getShortName() != null) {
            helpString = MessageFormat.format("-{0} <{2}>, --{1} <{2}>: {3}\n",
                    optionDefinition.getShortName(),
                    optionDefinition.getLongName(),
                    getReadableOptionType(optionDefinition.getType()),
                    wrapNull(optionDefinition.getDescription()));
        } else {
            helpString = MessageFormat.format("--{0} <{1}>: {2}\n",
                    optionDefinition.getLongName(),
                    getReadableOptionType(optionDefinition.getType()),
                    wrapNull(optionDefinition.getDescription()));
        }
        return helpString;
    }

    private String getReadableOptionType(Class type) {
        if (type.isPrimitive()) {
            try {
                if (type.equals(Integer.TYPE)) {
                    return Integer.class.getSimpleName();
                }
                //Constructing wrapper class name from primitive class name
                return Class.forName("java.lang." + Character.toUpperCase(type.getName().charAt(0)) + type.getName()
                        .substring(
                                1)).getSimpleName();
            } catch (ClassNotFoundException e) {
                return type.getSimpleName();
            }
        }
        return type.getSimpleName();
    }

    /**
     * Returns empty string if value is null
     *
     * @param value value
     * @return "" if null, value otherwise
     */
    private String wrapNull(String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
}
