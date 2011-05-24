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

import com.googlecode.commandme.OperandDefinitionException;
import com.googlecode.commandme.annotations.Operand;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

/**
 * @author Dmitry Sidorenko
 */
public class OperandsIntrospectorTest {
    private OperandsIntrospector operandsIntrospector;

    @Before
    public void setUp() throws Exception {
        operandsIntrospector = new OperandsIntrospector<TestModule>(TestModule.class);
        operandsIntrospector.inspect();
    }

    @Test
    public void testGetOperands() throws Exception {
        assertThat(operandsIntrospector.getOperands().size(), is(2));
    }

    @Test
    public void testGetByLongName() throws Exception {
        assertThat(operandsIntrospector.getByLongName("greet").getLongName(), is("greet"));
        assertThat(operandsIntrospector.getByLongName("gr"), nullValue());
    }

    @Test
    public void testNotAccessibleOperand() throws Exception {
        assertThat(operandsIntrospector.getByLongName("privateer"), nullValue());
    }

    @Test
    public void testOperandWithParams() throws Exception {
        assertThat(operandsIntrospector.getByLongName("params"), nullValue());
    }

    @Test
    public void testSpecifyName() throws Exception {
        assumeThat(operandsIntrospector.getOperands(), notNullValue());

        assertThat(operandsIntrospector.getByLongName("ciao"), notNullValue());
        assertThat(operandsIntrospector.getByShortName("bb"), notNullValue());
    }

    @Test(expected = OperandDefinitionException.class)
    public void testDuplicateLongNames() throws Exception {
        operandsIntrospector = new OperandsIntrospector<TestModuleDupLongNames>(TestModuleDupLongNames.class);
        operandsIntrospector.inspect();
    }

    @Test(expected = OperandDefinitionException.class)
    public void testDuplicateShortNames() throws Exception {
        operandsIntrospector = new OperandsIntrospector<TestModuleDupShortNames>(TestModuleDupShortNames.class);
        operandsIntrospector.inspect();
    }

    @Test
    public void testInspect() throws Exception {
        assertThat(operandsIntrospector.getOperands(), notNullValue());
    }


    class TestModule {

        @Operand
        public void greet() {
        }

        @Operand(name = "ciao", shortName = "bb")
        public void bye() {
        }

        @Operand
        void privateer() {
        }

        @Operand
        public void params(String name) {
        }
    }

    class TestModuleDupLongNames {

        @Operand(name = "bye")
        public void greet() {
        }

        @Operand(name = "bye")
        public void bye() {
        }
    }

    class TestModuleDupShortNames {

        @Operand(shortName = "bb")
        public void greet() {
        }

        @Operand(shortName = "bb")
        public void bye() {
        }
    }
}
