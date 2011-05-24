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
 * Short form of boolean option, true
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446737">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_BooleanShort extends Story<Story_BooleanShort> {

    private boolean flag;
    private boolean good;

    @Test
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"-f", "true"});

        assertThat(commandLine.getModule().flag, is(true));
    }

    @Test
    public void testStoryTwo() throws Exception {
        commandLine.execute(new String[]{"-fg"});

        assertThat(commandLine.getModule().flag, is(true));
        assertThat(commandLine.getModule().good, is(true));
    }

    @Test
    public void testNotSet() throws Exception {
        commandLine.execute(new String[]{});

        assertThat(commandLine.getModule().flag, is(false));
        assertThat(commandLine.getModule().good, is(false));
    }

    @Option
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Option(shortName = "o")
    public void setGood(boolean good) {
        this.good = good;
    }
}
