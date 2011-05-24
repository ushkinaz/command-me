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

import com.googlecode.commandme.OptionDefinitionException;
import com.googlecode.commandme.annotations.Option;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"UnusedParameters"})
public class OptionsIntrospectorShortNamesTest {
    @Test
    public void testShortNames() throws Exception {
        OptionsIntrospector<ShortModule1> parameters = new OptionsIntrospector<ShortModule1>(ShortModule1.class);
        parameters.inspect();

        assertThat(parameters.getByShortName("n"), notNullValue());
        assertThat(parameters.getByShortName("m"), notNullValue());
        assertThat("Default short name not set", parameters.getByShortName("p"), notNullValue());
    }

    @Test(expected = OptionDefinitionException.class)
    public void testShortNamesBad() throws Exception {
        OptionsIntrospector<ShortModuleBad1> parameters = new OptionsIntrospector<ShortModuleBad1>(ShortModuleBad1.class);
        parameters.inspect();
    }

    @Test
    public void testShortNameDefault() throws Exception {
        OptionsIntrospector<ShortModuleDefault> parameters = new OptionsIntrospector<ShortModuleDefault>(ShortModuleDefault.class);
        parameters.inspect();
        assertThat(parameters.getByShortName("n"), notNullValue());
    }

    @Test
    public void testShortNameOverlap() throws Exception {
        OptionsIntrospector<ShortModuleOverlap> parameters = new OptionsIntrospector<ShortModuleOverlap>(ShortModuleOverlap.class);
        parameters.inspect();
        assertThat(parameters.getByShortName("n"), CoreMatchers.nullValue());
    }

    @Test(expected = OptionDefinitionException.class)
    public void testShortNamesBadSameShorts() throws Exception {
        OptionsIntrospector<ShortModuleBad2> parameters = new OptionsIntrospector<ShortModuleBad2>(ShortModuleBad2.class);
        parameters.inspect();
    }

    private static class ShortModule1 {
        @Option(shortName = "n")
        public void setName(String name) {
        }

        @Option(shortName = "m")
        public void setMessage(String message) {
        }

        @Option()
        public void setPassword(String pass) {
        }
    }

    private static class ShortModuleBad1 {
        @Option(shortName = "loong")
        public void setName(String name) {
        }
    }

    private static class ShortModuleOverlap {
        @Option
        public void setName(String name) {
        }

        @Option
        public void setNoise(String noise) {
        }
    }

    private static class ShortModuleDefault {
        @Option
        public void setName(String name) {
        }
    }

    private static class ShortModuleBad2 {
        @Option(shortName = "n")
        public void setFuss(String name) {
        }

        @Option(shortName = "n")
        public void setName(String name) {
        }
    }
}