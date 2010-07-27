package com.googlecode.commandme;

import com.googlecode.commandme.impl.CommandLineImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 * @date Jun 3, 2010
 */
public final class CLIParser {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(CLIParser.class);

    public static <T> CommandLine<T> createModule(Class<T> module) throws CliException {
        //noinspection unchecked
        return new CommandLineImpl<T>(module);
    }
}
