package com.googlecode.commandme.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Parses command line into a list of {@link CliParameter}'s
 *
 * @author Dmitry Sidorenko
 */
class ArgumentsParser {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(ArgumentsParser.class);

    private String[] arguments;

    /**
     * Creates a parser
     *
     * @param arguments arguments to parse
     */
    public ArgumentsParser(String[] arguments) {
        this.arguments = arguments;
    }

    /**
     * Does actual parsing.
     *
     * @return result of parsing. Never {@code null}
     */
    public List<CliParameter> parse() {
        List<CliParameter> result = new ArrayList<CliParameter>();

//        CliParameter parameter = new
        for (String arg: arguments) {
        }
        return null;
    }
}
