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

import com.googlecode.commandme.ActionInvocationException;
import com.googlecode.commandme.CLIParser;
import com.googlecode.commandme.CommandLine;
import org.junit.Test;

/**
 * Execute a method marked as @Action
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13644361">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_13644361 {

    @Test(expected = ActionInvocationException.class)
    public void testStory() throws Exception {
        CommandLine<Story_13644361> module = CLIParser.createModule(Story_13644361.class);
        module.execute(new String[]{"no-action"});
    }
}
