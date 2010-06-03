package com.googlecode.commandme;

/**
 * Actual option passed to an application
 *
 * @author Dmitry Sidorenko
 */
public interface CliOption {

    /**
     * Gets name of an option as passed in cli
     *
     * @return name
     */
    String getName();

    /**
     * Returns list of values
     *
     * @return list of values
     */
    String[] getValues();

    /**
     * Returns single value of an option. If there are more than one value, first will be returned
     *
     * @return returns single value.
     */
    String getValue();
}
