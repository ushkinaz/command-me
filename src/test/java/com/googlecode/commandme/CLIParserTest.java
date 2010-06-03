package com.googlecode.commandme;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
 * @date Jun 3, 2010
 */
public class CLIParserTest {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(CLIParserTest.class);

    @Test(expected = NullPointerException.class)
    public void testParseArgumentsNull() throws Exception {
        CLIParser.parseArguments(null);
    }

    @Test
    public void testParseArgumentsNotNull() throws Exception {
        assertThat(CLIParser.parseArguments(new String[]{}), notNullValue());
    }
}
