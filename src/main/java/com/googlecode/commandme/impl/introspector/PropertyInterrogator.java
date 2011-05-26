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

public interface PropertyInterrogator {
    /**
     * Sets value from it's string representation
     *
     * @param instance instance to set value
     * @param values   actual values
     */
    void setValue(Object instance, String... values);

    /**
     * Does this property type needs value to be set?
     * Common use case is boolean option.
     *
     * @return true if can be avoided
     */
    boolean needValue();
}
