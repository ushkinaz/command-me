package com.googlecode.commandme.impl;

import com.googlecode.commandme.CliParameter;
import com.googlecode.commandme.CommandLine;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
 * @date Jun 3, 2010
 */
public class CommandLineImplTest {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineImplTest.class);

    private CommandLine commandLine;

    private String[] arg1 = {
            "commit",
            "-m",
            "\"Add me\"",
            "--verbose"

    };

    @Before
    public void setUp() throws Exception {
    }

    @Test(expected = NullPointerException.class)
    public void testExecuteNull() throws Exception {
        commandLine = new CommandLineImpl(arg1);
        commandLine.execute(null);
    }


    @Test(expected = InstantiationException.class)
    public void testExecuteNoPublicConstructor() throws Exception {
        commandLine = new CommandLineImpl(arg1);
        commandLine.execute(NoConstructor.class);
    }

    @Test
    public void testGetParametersNotNull() throws Exception {
        commandLine = new CommandLineImpl(arg1);
        assertThat(commandLine.getParameters(), notNullValue());
    }

    @Test
    public void testGetParameters1() throws Exception {
        commandLine = new CommandLineImpl(arg1);
        List<CliParameter> parameters = commandLine.getParameters();
        assertThat(parameters.size(), CoreMatchers.is(4));
    }

    private class NoConstructor{

    }
}
