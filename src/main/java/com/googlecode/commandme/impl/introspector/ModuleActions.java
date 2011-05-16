package com.googlecode.commandme.impl.introspector;

import java.util.List;

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
    List<ActionDefinition> getActions();

    /**
     * Returns action by its full name
     *
     * @param name long name of an action
     * @return ParameterDefinition or null
     */
    ActionDefinition getByLongName(String name);

    /**
     * Returns action by it's short name.
     *
     * @param name short name of an action
     * @return ParameterDefinition or null
     */
    ActionDefinition getByShortName(String name);
}
