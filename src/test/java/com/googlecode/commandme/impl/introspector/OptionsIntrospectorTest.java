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
/**
 *
 * @author Dmitry Sidorenko
 */

import com.googlecode.commandme.OptionDefinitionException;
import com.googlecode.commandme.annotations.Action;
import com.googlecode.commandme.annotations.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"UnusedOptions"})
public class OptionsIntrospectorTest {

    @Test
    public void testModuleOptions() throws Exception {
        OptionsIntrospector<TestModule1> options = new OptionsIntrospector<TestModule1>(TestModule1.class);
        assertThat(options.getOptionDefinitions(), notNullValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetOptionDefinitions() throws Exception {
        OptionsIntrospector<TestModule1> options = new OptionsIntrospector<TestModule1>(TestModule1.class);
        options.getOptionDefinitions().clear();
    }

    @Test
    public void testAddOption() throws Exception {
        OptionsIntrospector<TestModule1> options = new OptionsIntrospector<TestModule1>(TestModule1.class);
        final OptionDefinition definition = new OptionDefinition();
        options.addOption(definition);
        assertThat(options.getOptionDefinitions().size(), is(1));
        assertThat(options.getOptionDefinitions().contains(definition), is(true));
    }

    @Test
    public void testGetByLongName() throws Exception {
        OptionsIntrospector<TestModule1> options = new OptionsIntrospector<TestModule1>(TestModule1.class);
        OptionDefinition definition = new OptionDefinition();
        definition.setLongName("foo");
        options.addOption(definition);

        OptionDefinition definitionBar = new OptionDefinition();
        definitionBar.setLongName("bar");
        options.addOption(definitionBar);

        assertThat(options.getByLongName("bar"), is(definitionBar));
        assertThat(options.getByLongName("foo"), is(definition));
    }


    @Test
    public void testGetByShortName() throws Exception {
        OptionsIntrospector<TestModule1> options = new OptionsIntrospector<TestModule1>(TestModule1.class);
        OptionDefinition definition = new OptionDefinition();
        definition.setShortName("f");
        options.addOption(definition);

        OptionDefinition definitionBar = new OptionDefinition();
        definitionBar.setLongName("bool");
        definitionBar.setShortName("b");
        options.addOption(definitionBar);

        assertThat(options.getByShortName("b"), is(definitionBar));
        assertThat(options.getByShortName("f"), is(definition));
    }

    @Test
    public void testInspectOptions() throws Exception {
        OptionsIntrospector<TestModule1> options = new OptionsIntrospector<TestModule1>(TestModule1.class);
        options.inspect();
        for (OptionDefinition optionDefinition : options.getOptionDefinitions()) {
            assertThat(optionDefinition.getShortName(), notNullValue());
            assertThat(optionDefinition.getShortName().length(), is(1));

            assertThat(optionDefinition.getLongName(), notNullValue());

            assertThat(optionDefinition.getDescription(), notNullValue());
            assertThat(optionDefinition.getType(), notNullValue());
        }

    }

    @Test
    public void testNonBeanCompliantParams() throws Exception {
        OptionsIntrospector<TestModule1> options = new OptionsIntrospector<TestModule1>(TestModule1.class);
        options.inspect();
        final OptionDefinition fooParam = options.getByLongName("label");
        assertThat(fooParam, notNullValue());
        assertThat(fooParam.getLongName(), is("label"));
        assertThat(fooParam.getShortName(), is("l"));
        assertEquals(String.class, fooParam.getType());
    }

    @Test
    public void testInspectOptionsValuesAreCorrect() throws Exception {
        OptionsIntrospector<TestModule1> options = new OptionsIntrospector<TestModule1>(TestModule1.class);
        options.inspect();
        final OptionDefinition fooParam = options.getByLongName("foo");
        assertThat(fooParam, notNullValue());
        assertThat(fooParam.getLongName(), is("foo"));
        assertThat(fooParam.getShortName(), is("f"));
        assertEquals(Integer.TYPE, fooParam.getType());
        assertThat(fooParam.getDescription(), is("none"));

        final OptionDefinition nameParam = options.getByLongName("name");
        assertThat(nameParam, notNullValue());
        assertThat(nameParam.getLongName(), is("name"));
        assertThat(nameParam.getShortName(), is("n"));
        assertEquals(String.class, nameParam.getType());
        assertThat(nameParam.getDescription(), is(""));


        for (OptionDefinition optionDefinition : options.getOptionDefinitions()) {
            assertThat(optionDefinition.getShortName(), notNullValue());
            assertThat(optionDefinition.getShortName().length(), is(1));

            assertThat(optionDefinition.getLongName(), notNullValue());

            assertThat(optionDefinition.getDescription(), notNullValue());
            assertThat(optionDefinition.getType(), notNullValue());
        }

    }

    @Test(expected = OptionDefinitionException.class)
    public void testBadSetter() throws Exception {
        OptionsIntrospector<BadModule1> options = new OptionsIntrospector<BadModule1>(BadModule1.class);
        options.inspect();

        final OptionDefinition fooParam = options.getByLongName("name");
        assertThat(fooParam, nullValue());
    }

    private static class BadModule1 {
        @Option(longName = "name")
        public void setName(String name, int age) {
        }

    }

    @Test
    public void testBadSetterAccess() throws Exception {
        OptionsIntrospector<BadModule2> options = new OptionsIntrospector<BadModule2>(BadModule2.class);
        options.inspect();

        final OptionDefinition fooParam = options.getByLongName("name");
        assertThat(fooParam, nullValue());
    }

    @Test
    public void testIntOption() throws Exception {
        OptionsIntrospector<IntModule> options = new OptionsIntrospector<IntModule>(IntModule.class);
        options.inspect();
        assertThat(options.getOptionDefinitions().size(), is(8));

        assertEquals(options.getByLongName("int").getType(), Integer.TYPE);
        assertEquals(options.getByLongName("intC").getType(), Integer.class);
    }

    private static class IntModule {

        @Option
        public void setInt(int id) {
        }

        @Option
        public void setIntC(Integer id) {
        }

        @Option
        public void setLong(long id) {
        }

        @Option
        public void setLongC(Long id) {
        }

        @Option
        public void setByte(byte id) {
        }

        @Option
        public void setByteC(Byte id) {
        }

        @Option
        public void setShort(short id) {
        }


        @Option
        public void setShortC(Short id) {
        }
    }

    private static class BadModule2 {
        @Option
        void setName(String name) {
        }

    }

    private static class TestModule1 {

        @Option
        public void setName(String sd) {
        }

        @Option(longName = "label")
        public void labelIt(String label) {
        }

        @Option(longName = "foo", shortName = "f", description = "none")
        public void setNoName(int i) {
        }

        @Action
        public void greet() {

        }
    }
}
