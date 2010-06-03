package com.googlecode.commandme.impl;

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.CliParameter;
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

    public <T> T execute(Class<T> clz) throws CliException {
        T instance;
        try {
            instance = clz.newInstance();
        } catch (InstantiationException e) {
            LOGGER.error("Error", e);
            throw new CliException(e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Error", e);
            throw new CliException(e);
        }catch (NullPointerException e){
            LOGGER.error("Error", e);
            throw new CliException(e);
        }
        return instance;
    }

    public List<CliParameter> getParameters() {
        return null;
    }
}
