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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Sidorenko
 */
@SuppressWarnings({"UnusedParameters"})
@RunWith(Parameterized.class)
public class DefaultPropertyInterrogatorParametrizedTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyInterrogatorParametrizedTest.class);

    protected DefaultPropertyInterrogator interrogator;
    protected boolean                     called;

    protected final String methodName;
    protected final Class  parameterType;
    protected final String value;

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"setString", String.class, "OneString"},

                {"setLongClass", Long.class, "100"},
                {"setLong", Long.TYPE, "100"},

                {"setIntClass", Integer.class, "100"},
                {"setInt", Integer.TYPE, "100"},

                {"setFloatClass", Float.class, "100.0"},
                {"setFloat", Float.TYPE, "100.0"},

                {"setDoubleClass", Double.class, "100.0"},
                {"setDouble", Double.TYPE, "100.0"},

                {"setByteClass", Byte.class, "100"},
                {"setByte", Byte.TYPE, "100"},

                {"setShortClass", Short.class, "100"},
                {"setShort", Short.TYPE, "100"},

                {"setBooleanClass", Boolean.class, "true"},
                {"setBoolean", Boolean.TYPE, "true"},

                {"setDate", Date.class, "01/02/11"},
        });
    }

    public DefaultPropertyInterrogatorParametrizedTest(String methodName, Class parameterType, String value) {
        LOGGER.info("Testing with method:{}, type:{}, value:{}", new Object[]{methodName, parameterType, value});
        this.methodName = methodName;
        this.parameterType = parameterType;
        this.value = value;

    }

    @Before
    public void setUp() throws Exception {
        ParameterDefinition parameterDefinitionString = mock(ParameterDefinition.class);
        when(parameterDefinitionString.getType()).thenReturn(parameterType);

        when(parameterDefinitionString.getWriterMethod()).thenReturn(DefaultPropertyInterrogatorParametrizedTest.class.getMethod(methodName, parameterType));
        interrogator = new DefaultPropertyInterrogator(parameterDefinitionString);
    }

    @Test
    public void testSetValue() throws NoSuchMethodException {

        interrogator.setValue(this, value);
        assertThat(called, is(true));
    }

    public void setString(String stringValue) {
        this.called = true;
    }

    public void setLongClass(Long stringValue) {
        this.called = true;
    }

    public void setLong(long stringValue) {
        this.called = true;
    }

    public void setIntClass(Integer stringValue) {
        this.called = true;
    }

    public void setInt(int stringValue) {
        this.called = true;
    }

    public void setFloatClass(Float stringValue) {
        this.called = true;
    }

    public void setFloat(float stringValue) {
        this.called = true;
    }

    public void setDoubleClass(Double stringValue) {
        this.called = true;
    }

    public void setDouble(double stringValue) {
        this.called = true;
    }

    public void setByteClass(Byte stringValue) {
        this.called = true;
    }

    public void setByte(byte stringValue) {
        this.called = true;
    }

    public void setShortClass(Short stringValue) {
        this.called = true;
    }

    public void setShort(short stringValue) {
        this.called = true;
    }

    public void setBooleanClass(Boolean stringValue) {
        this.called = true;
    }

    public void setBoolean(boolean stringValue) {
        this.called = true;
    }

    public void setDate(Date stringValue) {
        this.called = true;
    }
}
