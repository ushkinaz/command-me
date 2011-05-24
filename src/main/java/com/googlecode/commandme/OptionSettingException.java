package com.googlecode.commandme;

/**
 * @author Dmitry Sidorenko
 */
public class ParameterSettingException extends CliException {

    public ParameterSettingException(String message) {
        super(message);
    }

    public ParameterSettingException(String message, Throwable cause) {
        super(message, cause);
    }
}
