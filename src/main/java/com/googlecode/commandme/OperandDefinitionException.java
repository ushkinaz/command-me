package com.googlecode.commandme;

import com.googlecode.commandme.impl.introspector.OperandDefinition;

/**
 * @author Dmitry Sidorenko
 */
public class OperandDefinitionException extends CliException {

    private final OperandDefinition actionDefinition;

    public OperandDefinitionException(String message, OperandDefinition actionDefinition) {
        super(message);
        this.actionDefinition = actionDefinition;
    }

    public OperandDefinition getActionDefinition() {
        return actionDefinition;
    }
}
