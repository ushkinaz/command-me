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

public class ParameterDefinitionTest {
    private ParameterDefinition parameterDefinition;

    @Before
    public void setup() {
        parameterDefinition = new ParameterDefinition();
    }

    @Test(expected = IllegalStateException.class)
    public void testSetLongNameCantBeSetTwice() throws Exception {
        parameterDefinition.setLongName("One");
        parameterDefinition.setLongName("Two");
    }

    @Test
    public void testSetLongName() throws Exception {
        parameterDefinition.setLongName("One");
        Assert.assertThat(parameterDefinition.getLongName(), CoreMatchers.is("One"));
    }

    @Test
    public void testSetShortName() throws Exception {
        parameterDefinition.setShortName("One");
        Assert.assertThat(parameterDefinition.getShortName(), CoreMatchers.is("One"));
    }

    @Test(expected = IllegalStateException.class)
    public void testSetShortNameCantBeSetTwice() throws Exception {
        parameterDefinition.setShortName("One");
        parameterDefinition.setShortName("Two");
    }
}