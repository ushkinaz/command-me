package com.googlecode.commandme.impl;

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.CommandLine;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
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

    @Test(expected = CliException.class)
    public void testExecuteNull() throws Exception {
        commandLine = new CommandLineImpl<Object>(null);
        commandLine.execute(arg1);
    }


    @Test(expected = CliException.class)
    public void testExecuteNoPublicConstructor() throws Exception {
        commandLine = new CommandLineImpl<NoConstructor>(NoConstructor.class);
        commandLine.execute(arg1);
    }

    @Test
    public void testGetParametersNotNull() throws Exception {
        commandLine = new CommandLineImpl<Object>(Object.class);
        commandLine.execute(arg1);
        assertThat(commandLine.getParameters(), notNullValue());
    }

    @Test
    public void testGetParameters1() throws Exception {
        commandLine = new CommandLineImpl<Object>(Object.class);
        commandLine.execute(arg1);
        List<CliParameter> parameters = commandLine.getParameters();
        assertThat(parameters.size(), is(4));
    }

    @Test
    public void testCommandLineImpl() throws Exception {
        CommandLineImpl<Object> cli = new CommandLineImpl<Object>(Object.class);
        assertThat(cli.getModule(), notNullValue());

    }

    private class NoConstructor {

    }
}
