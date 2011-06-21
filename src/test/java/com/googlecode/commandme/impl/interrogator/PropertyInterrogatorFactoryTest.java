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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(Parameterized.class)
public class PropertyInterrogatorFactoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertyInterrogatorFactoryTest.class);

    @Parameterized.Parameters
    public static List<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {String.class},

                {Long.class},
                {Long.TYPE},

                {Integer.class},
                {Integer.TYPE},

                {Float.class},
                {Float.TYPE},

                {Double.class},
                {Double.TYPE},

                {Byte.class},
                {Byte.TYPE},

                {Short.class},
                {Short.TYPE},

                {Boolean.class},
                {Boolean.TYPE},

                {Date.class},
        });
    }

    private Class klass;

    public PropertyInterrogatorFactoryTest(Class klass) {
        LOGGER.debug("Class: " + klass);
        this.klass = klass;
    }

    @Test
    public void testCreateInterrogatorInternal() throws Exception {
        OptionDefinition optionDefinition = new OptionDefinition();
        optionDefinition.setType(klass);
        PropertyVivisector vivisector = PropertyInterrogatorFactory.createInterrogator(optionDefinition);

        assertThat(vivisector, notNullValue());
    }
}
