package com.googlecode.commandme;

import com.googlecode.commandme.impl.introspector.OperandDefinition;

/**
 * @author Dmitry Sidorenko
 */
public class OperandDefinitionException extends CliException {

    private final OperandDefinition operandDefinition;

    public OperandDefinitionException(String message, OperandDefinition operandDefinition) {
        super(message);
        this.operandDefinition = operandDefinition;
    }

    public OperandDefinition getOperandDefinition() {
        return operandDefinition;
    }
}
