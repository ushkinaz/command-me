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

package com.googlecode.commandme.impl.introspector;

import com.googlecode.commandme.ActionDefinitionException;
import com.googlecode.commandme.annotations.Action;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Dmitry Sidorenko
 */
public class ActionsIntrospector<T> implements ModuleActions {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionsIntrospector.class);

    private final Class<T>                      clz;
    private final Set<ActionDefinition>         actions;
    private final Map<String, ActionDefinition> shortNames;
    private final Map<String, ActionDefinition> longNames;

    public ActionsIntrospector(Class<T> clz) {
        this.clz = clz;
        actions = new HashSet<ActionDefinition>();
        shortNames = new HashMap<String, ActionDefinition>();
        longNames = new HashMap<String, ActionDefinition>();
    }

    private void addAction(ActionDefinition actionDefinition) {
        actions.add(actionDefinition);
        ActionDefinition previous = longNames.put(actionDefinition.getLongName(), actionDefinition);
        checkClash(actionDefinition, previous);
        if (actionDefinition.getShortName() != null) {
            previous = shortNames.put(actionDefinition.getShortName(), actionDefinition);
            checkClash(actionDefinition, previous);
        }
    }

    private void checkClash(ActionDefinition actionDefinition, ActionDefinition previousDefinition) {
        if (previousDefinition != null) {
            LOGGER.error("Duplicate actions definitions");
            throw new ActionDefinitionException("Actions clashed: new=" + actionDefinition + ", old=" + previousDefinition, actionDefinition);
        }
    }

    @Override
    public Set<ActionDefinition> getActionDefinitions() {
        return actions;
    }

    @Override
    public ActionDefinition getByLongName(String name) {
        return longNames.get(name);
    }

    @Override
    public ActionDefinition getByShortName(String name) {
        //We suppose that there is only one possible short name
        return shortNames.get(name);
    }

    @Override
    public void inspect() {
        for (Method method : clz.getMethods()) {
            Action actionAnnotation = method.getAnnotation(Action.class);
            if (actionAnnotation == null) {
                continue;
            }

            if (method.getParameterTypes().length > 0) {
                LOGGER.warn("Actions with options are not yet supported: {}", method);
                continue;
            }

            ActionDefinition definition = new ActionDefinition();
            definition.setAction(method);
            if (actionAnnotation.name().length() > 0) {
                definition.setLongName(actionAnnotation.name());
            } else {
                definition.setLongName(method.getName());
            }
            if (actionAnnotation.shortName().length() > 0) {
                definition.setShortName(actionAnnotation.shortName());
            }
            if (actionAnnotation.description().length() > 0) {
                definition.setDescription(actionAnnotation.description());
            }
            addAction(definition);
        }
    }
}
