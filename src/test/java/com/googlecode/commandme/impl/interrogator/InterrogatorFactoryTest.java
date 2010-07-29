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

package com.googlecode.commandme.impl.interrogator;
/**
 *
 * @author Dmitry Sidorenko
 */

import com.googlecode.commandme.impl.introspector.ModuleParameters;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class InterrogatorFactoryTest {

    @Test
    public void testCreateInterrogator() throws Exception {
        @SuppressWarnings({"unchecked"})
        Interrogator interrogator = InterrogatorFactory.createInterrogator(this, null, new String[]{"-m", "\"one\"", "ci"});
        assertThat(interrogator, notNullValue());
    }

    @Test
    public void testSetFactory() throws Exception {
        InterrogatorFactory.setFactory(new InterrogatorFactory(){
            @Override
            protected <T> Interrogator create(T instance, ModuleParameters moduleParameters, String[] parameters) {
                return new MyInterrogator<T>(instance, moduleParameters, new String[]{"-m", "\"one\"", "ci"});
            }
        });
        @SuppressWarnings({"unchecked"})
        Interrogator interrogator = InterrogatorFactory.createInterrogator(this, null, new String[]{"-m", "\"one\"", "ci"});
        assertThat(interrogator, CoreMatchers.is(MyInterrogator.class));
    }

    private static class MyInterrogator<T> extends Interrogator<T> {
        public MyInterrogator(T instance, ModuleParameters moduleParameters, String[] parameters) {
            super(instance, moduleParameters, parameters);
        }
    }

}