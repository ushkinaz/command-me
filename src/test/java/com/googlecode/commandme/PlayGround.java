package com.googlecode.commandme;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 */
public class PlayGround {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayGround.class);

    public static void main(String[] args) {
        try {
            CLIParser.createModule(PlayGround.class).execute(args);
        } catch (CliException e) {
            LOGGER.error("Error", e);
        }
        args.hashCode();
    }
}
