package com.googlecode.commandme.impl.introspector;

import java.util.Set;

/**
 * Lists actions available for the module
 *
 * @author Dmitry Sidorenko
 */
public interface ModuleOperands {
    /**
     * Returns list of available actions
     *
     * @return list of actions
     */
    Set<OperandDefinition> getActions();

    /**
     * Returns action by its full name
     *
     * @param name long name of an action
     * @return ParameterDefinition or null
     */
    OperandDefinition getByLongName(String name);

    /**
     * Returns action by it's short name.
     *
     * @param name short name of an action
     * @return ParameterDefinition or null
     */
    OperandDefinition getByShortName(String name);

    /**
     * Inspects module
     */
    void inspect();
}
