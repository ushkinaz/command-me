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

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.annotations.Action;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Execute a method marked as @Action
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446725">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_ExecuteAction extends Story<Story_ExecuteAction> {

    private boolean called = false;

    @Action
    public void greet() {
        System.out.println("Hello");
        called = true;
    }

    @Action
    public void badboy() {
        System.out.println("Oh, my God!");
        throw new IllegalStateException("They've killed Kenny!");
    }

    @Test
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"greet"});

        assertThat("Method was not called", commandLine.getModule().called, is(true));
    }

    @Test(expected = CliException.class)
    public void testStoryWithException() throws Exception {
        commandLine.execute(new String[]{"badboy"});
    }

    @Test
    public void testNegativeStory() throws Exception {
        commandLine.execute(new String[]{});

        assertThat("Method was called", commandLine.getModule().called, is(false));
    }
}
