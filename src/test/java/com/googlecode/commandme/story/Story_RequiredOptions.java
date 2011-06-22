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

import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.annotations.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Optional and required options
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446755">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_RequiredOptions extends Story<Story_RequiredOptions> {

    private boolean flag;
    private Boolean dead;


    @Test
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"--flag", "--dead"});

        assertThat(commandLine.getModule().flag, is(true));
        assertThat(commandLine.getModule().dead, is(true));
    }

    @Test(expected = OptionSettingException.class)
    public void testNegativeStory() throws Exception {
        commandLine.execute(new String[]{"--dead"});
    }

    @Option(required = true)
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Option
    public void setDead(Boolean dead) {
        this.dead = dead;
    }

}
