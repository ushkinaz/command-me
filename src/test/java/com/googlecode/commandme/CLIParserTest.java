/*
 * Copyright (c) 2010-2011, Dmitry Sidorenko. All Rights Reserved.
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

package com.googlecode.commandme;

import com.googlecode.commandme.annotations.Parameter;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
 */
public class CLIParserTest {
    @SuppressWarnings({"UnusedDeclaration"})
    private static final Logger LOGGER = LoggerFactory.getLogger(CLIParserTest.class);

    @Test(expected = NullPointerException.class)
    public void testCreateModuleNull() throws CliException {
        CLIParser.createModule(null);
    }

    @Test
    public void testCreateModuleNotNull() throws Exception {
        assertThat(CLIParser.createModule(TestModule.class), notNullValue());
    }

    @Test
    public void testShutdown() throws Exception {
        CommandLine<TestModule> commandLine = CLIParser.createModule(TestModule.class);
        commandLine.execute(new String[]{});
        commandLine.shutdown();

        assertThat(commandLine.getModule(), nullValue());
    }

    public static class TestModule {
        public TestModule() {
        }

        @Parameter
        public void setName(String sd) {
        }

        @Parameter(longName = "foo", shortName = "f", description = "none", helpRequest = true)
        public void setNoName(int i) {
        }
    }

}
