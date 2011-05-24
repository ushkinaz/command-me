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
import com.googlecode.commandme.annotations.Operand;
import com.googlecode.commandme.annotations.Option;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"UnusedParameters"})
public class OptionsIntrospectorTest {

    @Test
    public void testModuleParameters() throws Exception {
        OptionsIntrospector<TestModule1> parameters = new OptionsIntrospector<TestModule1>(TestModule1.class);
        assertThat(parameters.getParameterDefinitions(), notNullValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetParameterDefinitions() throws Exception {
        OptionsIntrospector<TestModule1> parameters = new OptionsIntrospector<TestModule1>(TestModule1.class);
        parameters.getParameterDefinitions().clear();
    }

    @Test
    public void testAddParameter() throws Exception {
        OptionsIntrospector<TestModule1> parameters = new OptionsIntrospector<TestModule1>(TestModule1.class);
        final OptionDefinition definition = new OptionDefinition();
        parameters.addParameter(definition);
        assertThat(parameters.getParameterDefinitions().size(), is(1));
        assertThat(parameters.getParameterDefinitions().get(0), is(definition));
    }

    @Test
    public void testGetByLongName() throws Exception {
        OptionsIntrospector<TestModule1> parameters = new OptionsIntrospector<TestModule1>(TestModule1.class);
        OptionDefinition definition = new OptionDefinition();
        definition.setLongName("foo");
        parameters.addParameter(definition);

        OptionDefinition definitionBar = new OptionDefinition();
        definitionBar.setLongName("bar");
        parameters.addParameter(definitionBar);

        assertThat(parameters.getByLongName("bar"), is(definitionBar));
        assertThat(parameters.getByLongName("foo"), is(definition));
    }


    @Test
    public void testGetByShortName() throws Exception {
        OptionsIntrospector<TestModule1> parameters = new OptionsIntrospector<TestModule1>(TestModule1.class);
        OptionDefinition definition = new OptionDefinition();
        definition.setShortName("f");
        parameters.addParameter(definition);

        OptionDefinition definitionBar = new OptionDefinition();
        definitionBar.setLongName("bool");
        definitionBar.setShortName("b");
        parameters.addParameter(definitionBar);

        assertThat(parameters.getByShortName("b"), is(definitionBar));
        assertThat(parameters.getByShortName("f"), is(definition));
    }

    @Test
    public void testInspectParameters() throws Exception {
        OptionsIntrospector<TestModule1> parameters = new OptionsIntrospector<TestModule1>(TestModule1.class);
        parameters.inspect();
        for (OptionDefinition parameterDefinition : parameters.getParameterDefinitions()) {
            assertThat(parameterDefinition.getShortName(), notNullValue());
            assertThat(parameterDefinition.getShortName().length(), is(1));

            assertThat(parameterDefinition.getLongName(), notNullValue());

            assertThat(parameterDefinition.getDefaultValue(), notNullValue());
            assertThat(parameterDefinition.getDescription(), notNullValue());
            assertThat(parameterDefinition.getType(), notNullValue());
        }

    }

    @Test
    public void testNonBeanCompliantParams() throws Exception {
        OptionsIntrospector<TestModule1> parameters = new OptionsIntrospector<TestModule1>(TestModule1.class);
        parameters.inspect();
        final OptionDefinition fooParam = parameters.getByLongName("label");
        assertThat(fooParam, notNullValue());
        assertThat(fooParam.getLongName(), is("label"));
        assertThat(fooParam.getShortName(), is("l"));
        assertEquals(String.class, fooParam.getType());
    }

    @Test
    public void testInspectParametersValuesAreCorrect() throws Exception {
        OptionsIntrospector<TestModule1> parameters = new OptionsIntrospector<TestModule1>(TestModule1.class);
        parameters.inspect();
        final OptionDefinition fooParam = parameters.getByLongName("foo");
        assertThat(fooParam, notNullValue());
        assertThat(fooParam.getLongName(), is("foo"));
        assertThat(fooParam.getShortName(), is("f"));
        assertEquals(Integer.TYPE, fooParam.getType());
        assertThat(fooParam.getDefaultValue(), is("0"));
        assertThat(fooParam.getDescription(), is("none"));

        final OptionDefinition nameParam = parameters.getByLongName("name");
        assertThat(nameParam, notNullValue());
        assertThat(nameParam.getLongName(), is("name"));
        assertThat(nameParam.getShortName(), is("n"));
        assertEquals(String.class, nameParam.getType());
        assertThat(nameParam.getDefaultValue(), is(""));
        assertThat(nameParam.getDescription(), is(""));


        for (OptionDefinition parameterDefinition : parameters.getParameterDefinitions()) {
            assertThat(parameterDefinition.getShortName(), notNullValue());
            assertThat(parameterDefinition.getShortName().length(), is(1));

            assertThat(parameterDefinition.getLongName(), notNullValue());

            assertThat(parameterDefinition.getDefaultValue(), notNullValue());
            assertThat(parameterDefinition.getDescription(), notNullValue());
            assertThat(parameterDefinition.getType(), notNullValue());
        }

    }

    @Test(expected = OptionDefinitionException.class)
    public void testBadSetter() throws Exception {
        OptionsIntrospector<BadModule1> parameters = new OptionsIntrospector<BadModule1>(BadModule1.class);
        parameters.inspect();

        final OptionDefinition fooParam = parameters.getByLongName("name");
        assertThat(fooParam, nullValue());
    }

    private static class BadModule1 {
        @Option(longName = "name")
        public void setName(String name, int age) {
        }

    }

    @Test
    public void testBadSetterAccess() throws Exception {
        OptionsIntrospector<BadModule2> parameters = new OptionsIntrospector<BadModule2>(BadModule2.class);
        parameters.inspect();

        final OptionDefinition fooParam = parameters.getByLongName("name");
        assertThat(fooParam, nullValue());
    }

    @Test
    public void testIntParameter() throws Exception {
        OptionsIntrospector<IntModule> parameters = new OptionsIntrospector<IntModule>(IntModule.class);
        parameters.inspect();
        assertThat(parameters.getParameterDefinitions().size(), is(8));

        assertEquals(parameters.getByLongName("int").getType(), Integer.TYPE);
        assertEquals(parameters.getByLongName("intC").getType(), Integer.class);
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

        @Option(longName = "foo", shortName = "f", description = "none", helpRequest = true)
        public void setNoName(int i) {
        }

        @Operand
        public void greet() {

        }
    }
}
