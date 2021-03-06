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

package com.googlecode.commandme.impl.introspector;
/**
 *
 * @author Dmitry Sidorenko
 */

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OptionDefinitionTest {
    private OptionDefinition optionDefinition;

    @Before
    public void setup() {
        optionDefinition = new OptionDefinition();
    }

    @Test(expected = IllegalStateException.class)
    public void testSetLongNameCantBeSetTwice() throws Exception {
        optionDefinition.setLongName("One");
        optionDefinition.setLongName("Two");
    }

    @Test
    public void testSetLongName() throws Exception {
        optionDefinition.setLongName("One");
        Assert.assertThat(optionDefinition.getLongName(), CoreMatchers.is("One"));
    }

    @Test
    public void testSetShortName() throws Exception {
        optionDefinition.setShortName("One");
        Assert.assertThat(optionDefinition.getShortName(), CoreMatchers.is("One"));
    }

    @Test(expected = IllegalStateException.class)
    public void testSetShortNameCantBeSetTwice() throws Exception {
        optionDefinition.setShortName("One");
        optionDefinition.setShortName("Two");
    }
}