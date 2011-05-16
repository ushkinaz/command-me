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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ModuleIntrospectorTest {
    private ModuleIntrospector moduleIntrospector;

    @Before
    public void setupTest() {
    }

    @Test
    public void testModuleIntrospector() throws Exception {
        moduleIntrospector = new ModuleIntrospector<TestModule1>(TestModule1.class);
        assertEquals(moduleIntrospector.getClz(), TestModule1.class);
    }

    @Test
    public void testInspectParameters() throws Exception {
        moduleIntrospector = new ModuleIntrospector<TestModule1>(TestModule1.class);
        moduleIntrospector.inspect();
        for (ParameterDefinition parameterDefinition : moduleIntrospector.getParameters().getParameterDefinitions()) {
            assertThat(parameterDefinition.getShortName(), notNullValue());
            assertThat(parameterDefinition.getShortName().length(), is(1));

            assertThat(parameterDefinition.getLongName(), notNullValue());

            assertThat(parameterDefinition.getDefaultValue(), notNullValue());
            assertThat(parameterDefinition.getDescription(), notNullValue());
            assertThat(parameterDefinition.getType(), notNullValue());
        }

    }

    @Test
    public void testInspectParametersValuesAreCorrect() throws Exception {
        moduleIntrospector = new ModuleIntrospector<TestModule1>(TestModule1.class);
        moduleIntrospector.inspect();
        final ParameterDefinition fooParam = moduleIntrospector.getParameters().getByLongName("foo");
        assertThat(fooParam, notNullValue());
        assertThat(fooParam.getLongName(), is("foo"));
        assertThat(fooParam.getShortName(), is("f"));
        assertEquals(Integer.TYPE, fooParam.getType());
        assertThat(fooParam.getDefaultValue(), is("0"));
        assertThat(fooParam.getDescription(), is("none"));

        final ParameterDefinition nameParam = moduleIntrospector.getParameters().getByLongName("name");
        assertThat(nameParam, notNullValue());
        assertThat(nameParam.getLongName(), is("name"));
        assertThat(nameParam.getShortName(), is("n"));
        assertEquals(String.class, nameParam.getType());
        assertThat(nameParam.getDefaultValue(), is(""));
        assertThat(nameParam.getDescription(), is(""));


        for (ParameterDefinition parameterDefinition : moduleIntrospector.getParameters().getParameterDefinitions()) {
            assertThat(parameterDefinition.getShortName(), notNullValue());
            assertThat(parameterDefinition.getShortName().length(), is(1));

            assertThat(parameterDefinition.getLongName(), notNullValue());

            assertThat(parameterDefinition.getDefaultValue(), notNullValue());
            assertThat(parameterDefinition.getDescription(), notNullValue());
            assertThat(parameterDefinition.getType(), notNullValue());
        }

    }

    @Test
    public void testGetParameters() throws Exception {
        //TODO: Implement
        Assert.fail();
    }

    @Test
    public void testGetActions() throws Exception {
        //TODO: Implement
        Assert.fail();
    }

    @Test
    public void testInspectActions() throws Exception {
        //TODO: Implement
        Assert.fail();
    }
}