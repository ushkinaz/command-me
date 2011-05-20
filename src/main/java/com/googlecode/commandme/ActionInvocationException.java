package com.googlecode.commandme;

/**
 * @author Dmitry Sidorenko
 */
public class ActionInvocationException extends CliException {

    public ActionInvocationException(String message) {
        super(message);
    }

    public ActionInvocationException(String message, Throwable cause) {
        super(message, cause);
    }
}
