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

import com.googlecode.commandme.impl.introspector.OptionDefinition;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class BooleanPropertyInterrogatorTest {
    private BooleanPropertyVivisector interrogator;
    private Boolean                   name;

    @Before
    public void setUp() throws Exception {
        OptionDefinition optionDefinition = new OptionDefinition();
        optionDefinition.setType(String.class);
        optionDefinition.setWriterMethod(BooleanPropertyInterrogatorTest.class.getMethod("setName", Boolean.class));

        interrogator = new BooleanPropertyVivisector(optionDefinition);
    }

    @Test
    public void testSetValue() throws Exception {
        interrogator.setValue(this, "");

        assertThat(name, is(true));
    }

    @Test
    public void testNeedValue() throws Exception {
        assertThat(interrogator.needValue(), is(false));
    }

    @SuppressWarnings({"UnusedDeclaration"})
    public void setName(Boolean name) {
        this.name = name;
    }
}
