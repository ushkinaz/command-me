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

import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.annotations.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Pass double type parameter to an action using full name
 * <p/>
 * <a href="https://www.pivotaltracker.com/story/show/13446735">story</a>
 *
 * @author Dmitry Sidorenko
 */
public class Story_DoubleParams extends Story<Story_DoubleParams> {

    private double amount;
    private Double money;
    private float  bet;


    @Test
    public void testStory() throws Exception {
        commandLine.execute(new String[]{"--amount", "100.0", "--money", "-30", "--bet", "400"});

        assertThat(commandLine.getModule().amount, is(100.0));
        assertThat(commandLine.getModule().money, is(-30.0));
        assertThat(commandLine.getModule().bet, is((float) 400.0));
    }

    @Test(expected = OptionSettingException.class)
    public void testNegativeStory() throws Exception {
        commandLine.execute(new String[]{"--flag", "issue"});
    }

    @Option
    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Option
    public void setMoney(Double money) {
        this.money = money;
    }

    @Option
    public void setBet(float bet) {
        this.bet = bet;
    }
}
