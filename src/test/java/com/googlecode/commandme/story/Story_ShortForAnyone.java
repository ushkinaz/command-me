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
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Use short names of any types of options<br/>
 * <code>HelloWorld greet -t 2 -h -g 400 -n "John Woo"</code>
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446747">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_ShortForAnyone extends Story<Story_ShortForAnyone> {

    private String  name;
    private boolean truth;
    private int     times;

    @Option
    public void setTimes(int times) {
        this.times = times;
    }

    @Option(shortName = "h")
    public void setTruth(boolean truth) {
        this.truth = truth;
    }

    @Option
    public void setName(String name) {
        this.name = name;
    }


    @Test
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"-t", "2", "-h", "-n", "John Woo"});

        assertThat(commandLine.getModule().truth, is(true));
        assertThat(commandLine.getModule().name, is("John Woo"));
        assertThat(commandLine.getModule().times, is(2));
    }

    @Test
    public void testNegativeStory() throws Exception {
        commandLine.execute(new String[]{});

        assertThat(commandLine.getModule().truth, is(false));
        assertThat(commandLine.getModule().name, nullValue());
        assertThat(commandLine.getModule().times, is(0));
    }
}
