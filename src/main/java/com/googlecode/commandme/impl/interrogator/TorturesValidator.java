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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.impl.interrogator.tortures.TortureHelp;
import com.googlecode.commandme.impl.interrogator.tortures.TortureInstrument;
import com.googlecode.commandme.impl.interrogator.tortures.TortureOption;
import com.googlecode.commandme.impl.introspector.OptionDefinition;

public class TorturesValidator<T> {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(TorturesValidator.class);

    private final List<TortureInstrument> tortures;
    private final Set<OptionDefinition>   optionDefinitions;

    public TorturesValidator(List<TortureInstrument> tortures, Set<OptionDefinition> optionDefinitions) {
        this.tortures = tortures;
        this.optionDefinitions = optionDefinitions;
    }

    void validateTorturesPlan() {
        Set<OptionDefinition> options = new HashSet<OptionDefinition>();

        for (TortureInstrument instrument : tortures) {
            instrument.validate();

            if (instrument instanceof TortureHelp) {
                if (tortures.size() > 1) {
                    throw new CliException("Help option does not accept arguments");
                } else {
                    // No need to do any consistency checks
                    return;
                }
            }

            // Build a set of options
            if (instrument instanceof TortureOption) {
                options.add(((TortureOption) instrument).getOptionDef());
            }
        }

        checkRequiredOptions(options);


        LOGGER.debug("Validation complete");

    }

    /**
     * Check that all required options are set
     *
     * @param options options present
     */
    private void checkRequiredOptions(Set<OptionDefinition> options) {
        for (OptionDefinition optionDefinition : optionDefinitions) {
            if (optionDefinition.isRequired() && !options.contains(optionDefinition)) {
                LOGGER.error("Required option " + optionDefinition + " not set");
                throw new OptionSettingException("Required option " + optionDefinition + " not set");
            }
        }
    }
}