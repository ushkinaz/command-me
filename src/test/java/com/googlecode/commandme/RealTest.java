package com.googlecode.commandme;

import com.googlecode.commandme.annotations.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 */
public class RealTest {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(RealTest.class);

    private String name;

    public static void main(String[] args) {
        CLIParser.createModule(RealTest.class).execute(args);
    }

    @Option
    public void setName(String name) {
        this.name = name;
    }
}
