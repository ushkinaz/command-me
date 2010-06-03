package com.googlecode.commandme.impl;

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implementation of command line.
 * Combines three phases: Definition, Parsing and Interrogation.
 *
 * @author Dmitry Sidorenko
 */
public class CommandLineImpl implements CommandLine {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineImpl.class);

    private List<CliParameter> parameters;

    private ArgumentsParser argumentsParser;
    private ModuleIntrospector moduleIntrospector;

    /**
     * Constructs an object
     *
     * @param arguments arguments to parse
     */
    public CommandLineImpl(String[] arguments) {
        argumentsParser = new ArgumentsParser(arguments);
    }

    /**
     * Does actual work.
     */
    public <T> T execute(Class<T> clz) throws CliException {
        parameters = argumentsParser.parse();

        moduleIntrospector = new ModuleIntrospector(clz);
        OptionsDefinition optionsDefinition = moduleIntrospector.inspect();

        T instance = createInstance(clz);

        Interrogator interrogator = new Interrogator(instance, optionsDefinition, parameters);
        interrogator.torture();

        return instance;
    }


    /**
     * Creates an instance of given class. The class should have public no-arg constructor.
     *
     * @param clz class
     * @return created instance, never {@code null}
     * @throws CliException exception if class does not have no-arg constructor, constructor is not accessible , if class is null
     */
    private <T> T createInstance(Class<T> clz) throws CliException {
        T instance;
        try {
            instance = clz.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("Error", e);
            throw new CliException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Error", e);
            throw new CliException(e);
        } catch (NullPointerException e) {
            LOGGER.error("Error", e);
            throw new CliException(e);
        }
        return instance;
    }

    public List<CliParameter> getParameters() {
        return parameters;
    }

}
