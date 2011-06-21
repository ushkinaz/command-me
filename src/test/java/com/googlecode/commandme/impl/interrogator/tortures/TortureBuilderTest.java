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

package com.googlecode.commandme.impl.interrogator.tortures;

import com.googlecode.commandme.CliException;
import com.googlecode.commandme.impl.introspector.ModuleIntrospector;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * @author Dmitry Sidorenko
 */
public class TortureBuilderTest {
    @SuppressWarnings({"unused"})
    private static final Logger LOGGER = LoggerFactory.getLogger(TortureBuilderTest.class);
    private ModuleIntrospector<TortureBuilderTest> moduleIntrospector;


    @Before
    public void setUp() throws Exception {
        //noinspection unchecked
        moduleIntrospector = Mockito.mock(ModuleIntrospector.class);
    }

    @Test
    public void testGetBuilder() throws Exception {
        assertThat(TortureBuilder.getBuilder(this, moduleIntrospector), notNullValue());
    }

    @Test
    public void testGetBuilderNotSame() throws Exception {
        assertThat(TortureBuilder.getBuilder(this, moduleIntrospector), not(sameInstance(TortureBuilder.getBuilder(this, moduleIntrospector))));
    }

    @Test(expected = CliException.class)
    public void testFinishNotBuiltAction() throws Exception {
        TortureBuilder builder = TortureBuilder.getBuilder(this, moduleIntrospector);
        builder.startBuilding(TortureType.ACTION);
        builder.finishTortureInstrument();
    }

    @Test(expected = CliException.class)
    public void testFinishNotBuiltOption() throws Exception {
        TortureBuilder builder = TortureBuilder.getBuilder(this, moduleIntrospector);
        builder.startBuilding(TortureType.OPTION);
        builder.finishTortureInstrument();
    }

    @Test
    public void testFinishNotBuiltHelp() throws Exception {
        TortureBuilder builder = TortureBuilder.getBuilder(this, moduleIntrospector);
        builder.startBuilding(TortureType.HELP);
        builder.finishTortureInstrument();
    }
}
