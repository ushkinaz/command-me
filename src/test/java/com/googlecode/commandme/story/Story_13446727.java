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


package com.googlecode.commandme.story;

import com.googlecode.commandme.CLIParser;
import com.googlecode.commandme.CommandLine;
import com.googlecode.commandme.annotations.Action;
import com.googlecode.commandme.annotations.Parameter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Execute a method marked as @Action
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446727">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_13446727 {

    private static final String JOHN_SMITH = "John Smith";
    private String name;

    @Parameter
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Action
    public void greet() {
        System.out.println("Hello, " + name);
    }

    @Test
    public void testStory() throws Exception {
        CommandLine<Story_13446727> module = CLIParser.createModule(Story_13446727.class);
        module.execute(new String[]{"greet", "--name", JOHN_SMITH});

        assertThat("Parameter was not set", module.getModule().getName(), is(JOHN_SMITH));
    }

    @Test
    public void testNegativeStory() throws Exception {
        CommandLine<Story_13446727> module = CLIParser.createModule(Story_13446727.class);
        module.execute(new String[]{"greet"});

        assertThat("Method was called", module.getModule().name, nullValue());
    }
}
