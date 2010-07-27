package com.googlecode.commandme.impl;

/**
 * Actual parameter passed to an application
 *
 * @author Dmitry Sidorenko
 */
public interface CliParameter extends CliElement {

    /**
     * Returns list of values
     *
     * @return list of values
     */
    String[] getValues();

    /**
     * Returns single value of an parameter. If there are more than one value, first will be returned
     *
     * @return returns single value.
     */
    String getValue();
}
