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

import java.util.Set;

/**
 * Lists actions available for the module
 *
 * @author Dmitry Sidorenko
 */
public interface ModuleActions {
    /**
     * Returns list of available actions
     *
     * @return list of actions
     */
    Set<ActionDefinition> getActionDefinitions();

    /**
     * Returns action by its full name
     *
     * @param name long name of an action
     * @return OptionDefinition or null
     */
    ActionDefinition getByLongName(String name);

    /**
     * Returns action by it's short name.
     *
     * @param name short name of an action
     * @return OptionDefinition or null
     */
    ActionDefinition getByShortName(String name);

    /**
     * Inspects module
     */
    void inspect();
}
