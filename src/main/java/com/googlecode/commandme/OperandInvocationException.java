package com.googlecode.commandme;

/**
 * @author Dmitry Sidorenko
 */
public class OperandInvocationException extends CliException {

    public OperandInvocationException(String message) {
        super(message);
    }

    public OperandInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
