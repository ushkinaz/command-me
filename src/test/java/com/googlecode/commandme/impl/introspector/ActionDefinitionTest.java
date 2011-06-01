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

package com.googlecode.commandme.impl.introspector;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @author Dmitry Sidorenko
 */
public class ActionDefinitionTest {

    public static final String LONG_NAME  = "long";
    public static final String SHORT_NAME = "shorty";
    public static final String DESC       = "desc";

    @Test
    public void testSetAction() throws Exception {
        ActionDefinition definition = new ActionDefinition();
        Method testSetAction = ActionDefinitionTest.class.getMethod("testSetAction");
        definition.setAction(testSetAction);
        Assert.assertThat(definition.getMethod(), CoreMatchers.is(testSetAction));
    }

    @Test
    public void testSetLongName() throws Exception {
        ActionDefinition definition = new ActionDefinition();
        definition.setLongName(LONG_NAME);
        Assert.assertThat(definition.getLongName(), CoreMatchers.is(LONG_NAME));
    }

    @Test
    public void testSetShortName() throws Exception {
        ActionDefinition definition = new ActionDefinition();
        definition.setShortName(SHORT_NAME);
        Assert.assertThat(definition.getShortName(), CoreMatchers.is(SHORT_NAME));
    }

    @Test
    public void testSetDescription() throws Exception {
        ActionDefinition definition = new ActionDefinition();
        definition.setDescription(DESC);
        Assert.assertThat(definition.getDescription(), CoreMatchers.is(DESC));
    }

}
