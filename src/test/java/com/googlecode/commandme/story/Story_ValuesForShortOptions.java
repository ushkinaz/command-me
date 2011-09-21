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

import com.googlecode.commandme.annotations.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Values for short options, using "="
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13969179">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_ValuesForShortOptions extends Story<Story_ValuesForShortOptions> {

    private int    count;
    private String name;


    @Test
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"-c=500", "-n=Joe"});

        assertThat(commandLine.getModule().count, is(500));
        assertThat(commandLine.getModule().name, is("Joe"));
    }

    @Test
    public void testStoryQuotes() throws Exception {
        commandLine.execute(new String[]{"-n=\"Joe Smith\""});

        assertThat(commandLine.getModule().count, is(0));
        assertThat(commandLine.getModule().name, is("Joe Smith"));
    }

    @Option
    public void setCount(int count) {
        this.count = count;
    }

    @Option
    public void setName(String name) {
        this.name = name;
    }
}
