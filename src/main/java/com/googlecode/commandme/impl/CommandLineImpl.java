package com.googlecode.commandme.impl;

import com.googlecode.commandme.CliOption;
import com.googlecode.commandme.CommandLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Dmitry Sidorenko
 * @date Jun 3, 2010
 */
public class CommandLineImpl implements CommandLine {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineImpl.class);

    public CommandLineImpl(String[] arguments) {
    }

    public <T> T execute(Class<T> clz) throws InstantiationException, IllegalAccessException {
        T instance = clz.newInstance();
        return instance;
    }

    public List<CliOption> getOptions() {
        return null;
    }
}
