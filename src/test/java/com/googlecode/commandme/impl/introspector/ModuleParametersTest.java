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
    private ModuleParameters moduleParameters;

    @Before
    public void setup() {
        moduleParameters = new ModuleParameters();
    }

    @Test
    public void testModuleParameters() throws Exception {
        assertThat(moduleParameters.getParameterDefinitions(), notNullValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetParameterDefinitions() throws Exception {
        moduleParameters.getParameterDefinitions().clear();
    }

    @Test
    public void testAddParameter() throws Exception {
        final ParameterDefinition definition = new ParameterDefinition();
        moduleParameters.addParameter(definition);
        assertThat(moduleParameters.getParameterDefinitions().size(), is(1));
        assertThat(moduleParameters.getParameterDefinitions().get(0), is(definition));
    }
}