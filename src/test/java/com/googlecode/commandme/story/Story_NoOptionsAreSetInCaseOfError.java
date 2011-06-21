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
import com.googlecode.commandme.impl.interrogator.VivisectorException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
 */
public class Story_NoOptionsAreSetInCaseOfError extends Story<Story_NoOptionsAreSetInCaseOfError> {

    private boolean flag;
    private int     count;


    @Test(expected = VivisectorException.class)
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"--flag", "--deadOption"});

        assertThat(commandLine.getModule().flag, is(false));
    }

    @Test(expected = VivisectorException.class)
    public void testStoryWrongType() throws Exception {
        commandLine.execute(new String[]{"--flag", "--count", "not-a-number"});

        assertThat(commandLine.getModule().flag, is(false));
        assertThat(commandLine.getModule().count, is(0));
    }

    @Option
    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Option
    public void setCount(int count) {
        this.count = count;
    }

}
