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

import com.googlecode.commandme.impl.introspector.ActionDefinition;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.OptionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 */
public class TortureBuilder<T> {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(TortureBuilder.class);

    private       TortureInstrument     instrument;
    private final T                     module;
    private final ModuleIntrospector<T> moduleIntrospector;

    public static <T> TortureBuilder<T> getBuilder(T module, ModuleIntrospector<T> moduleIntrospector) {
        return new TortureBuilder<T>(module, moduleIntrospector);
    }

    private TortureBuilder(T module, ModuleIntrospector<T> moduleIntrospector) {
        this.module = module;
        this.moduleIntrospector = moduleIntrospector;
    }

    public void startBuilding(TortureType tortureType) {
        switch (tortureType) {
            case ACTION:
                instrument = new TortureAction<T>(module);
                break;

            case OPTION:
                instrument = new TortureOption<T>(module);
                break;

            case HELP:
                instrument = new TortureHelp<T>(module, moduleIntrospector);
                break;
        }
    }

    public void addOption(OptionDefinition optionDefinition) {
        assert instrument instanceof TortureOption;
        ((TortureOption) instrument).setOptionDef(optionDefinition);
    }

    public void addValue(String value) {
        assert instrument instanceof TortureOption;
        ((TortureOption) instrument).setValue(value);
    }

    public void addAction(ActionDefinition actionDefinition) {
        assert instrument instanceof TortureAction;
        ((TortureAction) instrument).setActionDefinition(actionDefinition);
    }

    public TortureInstrument finishTortureInstrument() {
        assert instrument != null;

        instrument.validate();

        return instrument;
    }
}
