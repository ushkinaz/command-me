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

package com.googlecode.commandme.impl.interrogator;

import com.googlecode.commandme.OptionSettingException;
import com.googlecode.commandme.annotations.Option;
import com.googlecode.commandme.impl.introspector.*;
import org.hamcrest.CoreMatchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InterrogatorTest {
    private static final String JOHN_FROSTER = "John Froster";
    private static final String DEVELOPER    = "developer";
    private ModuleIntrospector<InterrogatorTest> moduleIntrospector;

    private String name;
    private String desc;
    private int    id;
    private int    amount;

    @Before
    public void setUp() throws Exception {
        //noinspection unchecked
        moduleIntrospector = mock(ModuleIntrospector.class);
        ModuleActions moduleActions = new ActionsIntrospector<InterrogatorTest>(InterrogatorTest.class);
        moduleActions.inspect();

        ModuleOptions moduleOptions = new OptionsIntrospector<InterrogatorTest>(InterrogatorTest.class);
        moduleOptions.inspect();

        when(moduleIntrospector.getActions()).thenReturn(moduleActions);
        when(moduleIntrospector.getOptions()).thenReturn(moduleOptions);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test(expected = OptionSettingException.class)
    public void testFirstNameFailed() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--notexisyts", "0", "--name", JOHN_FROSTER});
        interrogator.interrogate();

        assertThat(name, CoreMatchers.is(JOHN_FROSTER));
    }

    @Test
    public void testSetOneName() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--name", JOHN_FROSTER});
        interrogator.interrogate();

        assertThat(name, CoreMatchers.is(JOHN_FROSTER));
    }

    @Test
    public void testSetTwoNames() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--name", JOHN_FROSTER, "--desc", DEVELOPER});
        interrogator.interrogate();

        assertThat(name, CoreMatchers.is(JOHN_FROSTER));
        assertThat(desc, CoreMatchers.is(DEVELOPER));
    }

    @Test
    public void testSetInteger() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--id", "555"});
        interrogator.interrogate();

        assertThat(id, CoreMatchers.is(555));
    }

    @Test(expected = OptionSettingException.class)
    public void testSetIntegerWrongFormat() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--id", "555.0"});
        interrogator.interrogate();

        assertThat(id, CoreMatchers.is(555));
    }

    @Test(expected = OptionSettingException.class)
    public void testSetByteOutOf() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--idByte", "555"});
        interrogator.interrogate();

        assertThat(id, CoreMatchers.is(555));
    }

    @Test
    public void testSetByte() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--idByte", "122"});
        interrogator.interrogate();

        assertThat(id, CoreMatchers.is(122));
    }

    @Test
    public void testSetInt() throws Exception {
        Interrogator<InterrogatorTest> interrogator = new Interrogator<InterrogatorTest>(this, moduleIntrospector, new String[]{"--amount", "122"});
        interrogator.interrogate();

        assertThat(amount, CoreMatchers.is(122));
    }

    @Option
    public void setName(String name) {
        this.name = name;
    }

    @Option
    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Option
    public void setId(Integer id) {
        this.id = id;
    }

    @Option
    public void setIdByte(byte id) {
        this.id = id;
    }

    @Option
    public void setAmount(int amount) {
        this.amount = amount;
    }
}
