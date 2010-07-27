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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ModuleIntrospectorTest {
    private ModuleIntrospector moduleIntrospector;

    @Before
    public void setupTest() {
    }

    @Test
    public void testModuleIntrospector() throws Exception {
        moduleIntrospector = new ModuleIntrospector(TestModule1.class);
        Assert.assertEquals(moduleIntrospector.getClz(), TestModule1.class);
    }

    @Test
    public void testInspect() throws Exception {
        moduleIntrospector = new ModuleIntrospector(TestModule1.class);
        ModuleParameters moduleParameters = moduleIntrospector.inspect();
        assertThat(moduleParameters, notNullValue());
    }
}