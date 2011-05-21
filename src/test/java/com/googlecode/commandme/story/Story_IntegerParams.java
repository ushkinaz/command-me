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

import com.googlecode.commandme.annotations.Parameter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Pass integer parameter to an action using full name
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446729">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_IntegerParams extends Story<Story_IntegerParams> {

    private Integer id;
    private int     amount;
    private long    longer;

    @Parameter
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Parameter
    public void setId(Integer id) {
        this.id = id;
    }

    @Parameter
    public void setLong(Long longer) {
        this.longer = longer;
    }


    @Test
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"--id", "1", "--amount", "33", "--long", "4444"});

        assertThat(commandLine.getModule().id, is(1));
        assertThat(commandLine.getModule().amount, is(33));
        assertThat(commandLine.getModule().longer, is((long) 4444));
    }

    @Test
    public void testNegativeStory() throws Exception {
        commandLine.execute(new String[]{});

        assertThat(commandLine.getModule().amount, is(0));
        assertThat(commandLine.getModule().amount, is(0));
        assertThat(commandLine.getModule().longer, is((long) 0));
    }
}
