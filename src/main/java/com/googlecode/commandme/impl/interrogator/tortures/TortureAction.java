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

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.impl.introspector.ActionDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 */
public class TortureAction<T> extends TortureInstrument<T> {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(TortureAction.class);

    private ActionDefinition actionDefinition;

    TortureAction(T module) {
        super(module);
    }

    @Override
    public void torture() {
        try {
            actionDefinition.getMethod().invoke(module);
        } catch (Exception e) {
            LOGGER.warn("Exception", e);
            throw new CliException("Exception invoking action: " + actionDefinition, e);
        }

    }

    public void setActionDefinition(ActionDefinition actionDefinition) {
        this.actionDefinition = actionDefinition;
    }
}
