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
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.RETURNS_DEFAULTS;
import static org.mockito.Mockito.when;

/**
 * @author Dmitry Sidorenko
 */
public class DefaultPropertyInterrogatorTest {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultPropertyInterrogatorTest.class);

    private DefaultPropertyInterrogator interrogator;

    @Before
    public void setUp() throws Exception {
        OptionDefinition parameterDefinitionString = Mockito.mock(OptionDefinition.class, RETURNS_DEFAULTS);
        when(parameterDefinitionString.getType()).thenReturn(String.class);

        interrogator = new DefaultPropertyInterrogator(parameterDefinitionString);
    }

    @Test
    public void testNeedValue() {
        assertThat(interrogator.needValue(), is(true));
    }
}
