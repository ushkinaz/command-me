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

import com.googlecode.commandme.annotations.Action;
import com.googlecode.commandme.annotations.Option;
import org.junit.Test;

import java.util.Date;

/**
 * Default help facility will use list of all actions and options with optional descriptions
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446751">story</a>
 *
 * @author Dmitry Sidorenko
 */
@SuppressWarnings({"FieldCanBeLocal"})
public class Story_Help extends Story<Story_Help> {

    private boolean called = false;

    @Action(description = "Greet someone we love")
    public void greet() {
        System.out.println("Hello");
        called = true;
    }

    @Action
    public void badBoy() {
        System.out.println("Oh, my God!");
        throw new IllegalStateException("They've killed Kenny!");
    }

    private Integer id;
    private int     amount;
    private long    longer;

    @Option(description = "Amount of happyness to give")
    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Option(description = "Some cryptic ID")
    public void setId(Integer id) {
        this.id = id;
    }

    @Option(description = "Long option")
    public void setLong(Long longer) {
        this.longer = longer;
    }

    @Option(description = "Life is this long and  even longer")
    public void setLife(Long life) {
    }

    @Option(description = "Death time")
    public void setDateOfDeath(Date life) {
    }

    /**
     * I'm really lazy, so no output parsing here. Just rely on "no exception - all is good".
     */
    @Test
    public void testStory() {
        commandLine.execute(new String[]{"--help"});
    }

    @Test
    public void testStoryShortVersion() {
        commandLine.execute(new String[]{"-h"});
    }

    @Test
    public void testStoryQuestionMark() {
        commandLine.execute(new String[]{"-?"});
    }

    @Test
    public void testStoryHelpInAction() throws Exception {
        commandLine.execute(new String[]{"help"});
    }
}
