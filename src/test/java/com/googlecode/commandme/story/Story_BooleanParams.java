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
 * Pass boolean option to an action using full name
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446733">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_BooleanParams extends Story<Story_BooleanParams> {

    private boolean flag;
    private Boolean raised;
    private Boolean dead;


    @Test
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"--flag", "--dead"});

        assertThat(commandLine.getModule().flag, is(true));
        assertThat(commandLine.getModule().raised, nullValue());
        assertThat(commandLine.getModule().dead, is(true));
    }

    @Test
    public void testNotSet() throws Exception {
        commandLine.execute(new String[]{});

        assertThat(commandLine.getModule().flag, is(false));
        assertThat(commandLine.getModule().raised, nullValue());
        assertThat(commandLine.getModule().dead, nullValue());
    }

    @Test
    public void testNegativeStory() throws Exception {
        commandLine.execute(new String[]{});
        assertThat(commandLine.getModule().flag, is(false));
    }

    @Option
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Option
    public void setDead(Boolean dead) {
        this.dead = dead;
    }

    @Option
    public void setRaised(Boolean raised) {
        this.raised = raised;
    }
}
