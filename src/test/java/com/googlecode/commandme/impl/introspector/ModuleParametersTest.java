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

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ModuleParametersTest {
    private ParametersIntrospector parameters;

    @Before
    public void setup() {
        parameters = new ParametersIntrospector();
    }

    @Test
    public void testModuleParameters() throws Exception {
        assertThat(parameters.getParameterDefinitions(), notNullValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetParameterDefinitions() throws Exception {
        parameters.getParameterDefinitions().clear();
    }

    @Test
    public void testAddParameter() throws Exception {
        final ParameterDefinition definition = new ParameterDefinition();
        parameters.addParameter(definition);
        assertThat(parameters.getParameterDefinitions().size(), is(1));
        assertThat(parameters.getParameterDefinitions().get(0), is(definition));
    }

    @Test
    public void testGetByLongName() throws Exception {
        ParameterDefinition definition = new ParameterDefinition();
        definition.setLongName("foo");
        parameters.addParameter(definition);

        ParameterDefinition definitionBar = new ParameterDefinition();
        definitionBar.setLongName("bar");
        parameters.addParameter(definitionBar);

        assertThat(parameters.getByLongName("bar"), is(definitionBar));
        assertThat(parameters.getByLongName("foo"), is(definition));
    }

    @Test
    public void testGetByShortName() throws Exception {
        ParameterDefinition definition = new ParameterDefinition();
        definition.setShortName("f");
        parameters.addParameter(definition);

        ParameterDefinition definitionBar = new ParameterDefinition();
        definitionBar.setShortName("b");
        parameters.addParameter(definitionBar);

        assertThat(parameters.getByShortName("b"), is(definitionBar));
        assertThat(parameters.getByShortName("f"), is(definition));
    }
}