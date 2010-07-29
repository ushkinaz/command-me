package com.googlecode.commandme;

/**
 * Command line.
 *
 * @author Dmitry Sidorenko
 */
public interface CommandLine<T> {

    /**
     * Instantiates an instance of class T, fills it with parameters and calls appropriate methods
     *
     * @param arguments arguments to parse
     * @return instance of a class
     * @throws CliException an exception
     */
    T execute(String[] arguments) throws CliException;

    /**
     * Returns instance of a module
     *
     * @return module instance
     */
    T getModule();
}
