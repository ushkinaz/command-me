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


package com.googlecode.commandme.story;

import com.googlecode.commandme.annotations.Action;
import com.googlecode.commandme.annotations.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Pass string option to a module using full name
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446727">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_StringOptionAsFullName extends Story<Story_StringOptionAsFullName> {

    private static final String JOHN_SMITH = "John Smith";
    private String name;

    @Option
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
        commandLine.execute(new String[]{"greet", "--name", JOHN_SMITH});

        assertThat("Option was not set", commandLine.getModule().getName(), is(JOHN_SMITH));
    }

    @Test
    public void testNegativeStory() throws Exception {
        commandLine.execute(new String[]{"greet"});

        assertThat("Method was called", commandLine.getModule().name, nullValue());
    }
}
