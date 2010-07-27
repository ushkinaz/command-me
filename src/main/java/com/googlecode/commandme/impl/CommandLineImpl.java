package com.googlecode.commandme.impl;

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.CommandLine;
import com.googlecode.commandme.impl.interrogator.Interrogator;
import com.googlecode.commandme.impl.interrogator.InterrogatorFactory;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import com.googlecode.commandme.impl.introspector.ModuleIntrospectorFactory;
import com.googlecode.commandme.impl.introspector.OptionsDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Implementation of command line.
 * Combines three phases: Definition, Parsing and Interrogation.
 *
 * @author Dmitry Sidorenko
 */
public class CommandLineImpl<T> implements CommandLine {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineImpl.class);


    private ArgumentsParser argumentsParser;
    private ModuleIntrospector moduleIntrospector;

    private List<CliParameter> parameters;
    private OptionsDefinition optionsDefinition;

    private T instance;

    /**
     * Constructs an object
     *
     * @param clz class of a module
     * @throws CliException
     */
    public CommandLineImpl(Class<T> clz) throws CliException {

        moduleIntrospector = ModuleIntrospectorFactory.createIntrospector(clz);
        optionsDefinition = moduleIntrospector.inspect();

        instance = createInstance(clz);

    }

    /**
     * Does actual work.
     */
    public T execute(String[] arguments) throws CliException {
        argumentsParser = new ArgumentsParser(arguments);
        parameters = argumentsParser.parse();

        Interrogator interrogator = InterrogatorFactory.createInterrogator(instance, optionsDefinition, parameters);
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
    private T createInstance(Class<T> clz) throws CliException {
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

    public T getModule() {
        return instance;
    }

}
