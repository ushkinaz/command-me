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

import com.googlecode.commandme.ActionInvocationException;
import com.googlecode.commandme.annotations.Action;
import org.junit.Test;

/**
 * In case of any errors in cli parsing - output help screen
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/14858279">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_HelpIfError extends Story<Story_HelpIfError> {

    /**
     * I'm really lazy, so no output parsing here. Just rely on "no exception - all is good".
     */
    @Action
    @Test(expected = ActionInvocationException.class)
    public void testStory() {
        commandLine.execute(new String[]{"say something"});
    }
}
