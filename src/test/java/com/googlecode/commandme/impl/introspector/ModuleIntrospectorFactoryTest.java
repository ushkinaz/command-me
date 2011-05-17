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
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class ModuleIntrospectorFactoryTest {
    @Test
    public void testCreateIntrospector() throws Exception {
        ModuleIntrospector introspector = ModuleIntrospectorFactory.createIntrospector(Object.class);
        assertThat(introspector, CoreMatchers.<Object>notNullValue());
        assertEquals(Object.class, introspector.getClz());
    }

    @Test
    public void testSetFactory() throws Exception {
        ModuleIntrospectorFactory.setFactory(new ModuleIntrospectorFactory() {
            @Override
            protected ModuleIntrospector create(Class clz) {
                return new MyModuleIntrospector(clz);
            }
        });
        ModuleIntrospector introspector = ModuleIntrospectorFactory.createIntrospector(Object.class);
        assertThat(introspector, is(MyModuleIntrospector.class));
    }

    private static class MyModuleIntrospector extends ModuleIntrospector {
        public MyModuleIntrospector(Class clz) {
            super(clz);
        }
    }

}