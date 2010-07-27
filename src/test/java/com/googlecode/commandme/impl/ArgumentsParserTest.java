/*
 * Copyright (c) 2010-2010, Dmitry Sidorenko. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.commandme.impl;

import org.junit.*;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 *
 * @author ushkinaz
 */
public class ArgumentsParserTest {
    private ArgumentsParser argumentsParser;
    private static final String[] ARGUMENTS = {"-abc", "--name", "\"John Deere\"", "-r", "1", "commit"};


    public ArgumentsParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        argumentsParser = new ArgumentsParser(ARGUMENTS);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testParseConcatenated() {
        argumentsParser = new ArgumentsParser(new String[]{"-abc"});
        List<CliParameter> parsed = argumentsParser.parse();
        assertThat(parsed.size(), is(3));

        assertThat(parsed.get(0).getName(), is("a"));
        assertThat(parsed.get(0).getValue(), nullValue());

        assertThat(parsed.get(1).getName(), is("b"));
        assertThat(parsed.get(1).getValue(), nullValue());

        assertThat(parsed.get(2).getName(), is("c"));
        assertThat(parsed.get(2).getValue(), nullValue());
    }

    @Test
    public void testParseN() {
        argumentsParser = new ArgumentsParser(new String[]{"-abc"});
        List<CliParameter> parsed = argumentsParser.parse();
        assertThat(parsed.size(), is(3));

        assertThat(parsed.get(0).getName(), is("a"));
        assertThat(parsed.get(0).getValue(), nullValue());

        assertThat(parsed.get(1).getName(), is("b"));
        assertThat(parsed.get(1).getValue(), nullValue());

        assertThat(parsed.get(2).getName(), is("c"));
        assertThat(parsed.get(2).getValue(), nullValue());
    }

    @Test
    public void testParseNotNull() {
        argumentsParser = new ArgumentsParser(new String[]{"-abc"});
        List<CliParameter> parsed = argumentsParser.parse();
        assertThat(parsed, notNullValue());
    }
}