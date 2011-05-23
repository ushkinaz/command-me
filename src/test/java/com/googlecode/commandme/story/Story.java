package com.googlecode.commandme.story;

import com.googlecode.commandme.CLIParser;
import com.googlecode.commandme.CommandLine;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Dmitry Sidorenko
 */
public abstract class Story<T extends Story> {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(Story.class);
    protected CommandLine<T> commandLine;

    @Before
    public void setUpStory() throws Exception {
        commandLine = (CommandLine<T>) CLIParser.createModule(getClass());
    }

    @After
    public void tearDownStory() throws Exception {
        commandLine.shutdown();
    }
}
