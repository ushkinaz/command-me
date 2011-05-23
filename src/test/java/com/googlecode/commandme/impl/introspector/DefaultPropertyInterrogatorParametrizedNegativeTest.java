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

import com.googlecode.commandme.ParameterSettingException;
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
@RunWith(Parameterized.class)
public class DefaultPropertyInterrogatorParametrizedNegativeTest extends DefaultPropertyInterrogatorParametrizedTest {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyInterrogatorParametrizedNegativeTest.class);

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {"setLongClass", Long.class, "p100"},
                {"setLong", Long.TYPE, "p100"},

                {"setIntClass", Integer.class, "p100"},
                {"setInt", Integer.TYPE, "p100"},

                {"setFloatClass", Float.class, "=100.0"},
                {"setFloat", Float.TYPE, "=100.0"},

                {"setDoubleClass", Double.class, "=100.0"},
                {"setDouble", Double.TYPE, "=100.0"},

                {"setByteClass", Byte.class, "0+100"},
                {"setByte", Byte.TYPE, "0+100"},

                {"setShortClass", Short.class, "0+100"},
                {"setShort", Short.TYPE, "0+100"},

                {"setDate", Date.class, "0+"},
        });
    }

    public DefaultPropertyInterrogatorParametrizedNegativeTest(String methodName, Class parameterType, String value) {
        super(methodName, parameterType, value);
    }

    @Test(expected = ParameterSettingException.class)
    public void testSetValue() throws NoSuchMethodException {
        ParameterDefinition parameterDefinitionString = mock(ParameterDefinition.class);
        when(parameterDefinitionString.getType()).thenReturn(parameterType);

        when(parameterDefinitionString.getWriterMethod()).thenReturn(DefaultPropertyInterrogatorParametrizedNegativeTest.class
                .getMethod(methodName, parameterType));

        interrogator.setValue(this, value);
        assertThat(called, is(false));
    }
}
