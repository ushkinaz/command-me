package com.googlecode.commandme.impl.introspector;

import java.util.Set;

/**
 * Lists operands available for the module
 *
 * @author Dmitry Sidorenko
 */
public interface ModuleOperands {
    /**
     * Returns list of available operands
     *
     * @return list of operands
     */
    Set<OperandDefinition> getOperands();

    /**
     * Returns operand by its full name
     *
     * @param name long name of an operand
     * @return OptionDefinition or null
     */
    OperandDefinition getByLongName(String name);

    /**
     * Returns operand by it's short name.
     *
     * @param name short name of an operand
     * @return OptionDefinition or null
     */
    OperandDefinition getByShortName(String name);

    /**
     * Inspects module
     */
    void inspect();
}
