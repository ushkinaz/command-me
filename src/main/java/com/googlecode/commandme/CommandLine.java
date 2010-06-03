package com.googlecode.commandme;

import com.googlecode.commandme.impl.CliParameter;

import java.util.List;

/**
 * Command line.
 *
 * @author Dmitry Sidorenko
 */
public interface CommandLine {

    /**
     * Instantiates an instance of class T, fills it with parameters and calls appropriate methods
     *
     * @param clz class to instantiate
     * @param <T> type
     * @return instance of a class
     * @throws CliException an exception
     */
    <T> T execute(Class<T> clz) throws CliException;

    /**
     * Returns list of parameters.
     *
     * @return parameters
     */
    List<CliParameter> getParameters();
}
