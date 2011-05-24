package com.googlecode.commandme;

/**
 * @author Dmitry Sidorenko
 */
public class OptionSettingException extends CliException {

    public OptionSettingException(String message) {
        super(message);
    }

    public OptionSettingException(String message, Throwable cause) {
        super(message, cause);
    }
}
