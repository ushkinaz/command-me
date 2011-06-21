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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.commandme.impl.introspector.OptionDefinition;

/**
 * @author Dmitry Sidorenko
 */
public class TortureOption<T> extends TortureInstrument<T> {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(TortureOption.class);

    private OptionDefinition optionDef;
    private String           value;


    TortureOption(T module) {
        super(module);
        value = "magic";
    }

    public void setOptionDef(OptionDefinition optionDef) {
        this.optionDef = optionDef;
    }

    public OptionDefinition getOptionDef() {
        return optionDef;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void torture() {
        assert optionDef != null;
        assert value != null;

        optionDef.getVivisector().vivisect(module);
    }

    @Override
    public void validate() throws TortureException {
        if (optionDef == null || value == null) {
            throw new TortureException("Torture :" + this + " is not valid");
        }

        optionDef.getVivisector().prepare(value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("TortureOption");
        sb.append("{optionDef=").append(optionDef);
        sb.append(", value='").append(value).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
