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

package com.googlecode.commandme.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines an operand
 *
 * @author Dmitry Sidorenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Operand {
    /**
     * The name of this operand.
     * Default is to have name equals to method name.
     *
     * @return The name of this operand
     */
    String name() default "";

    /**
     * Short name of this operand.
     * No defaults.
     *
     * @return Short name of this operand
     */
    String shortName() default "";

    /**
     * A description of this operand
     *
     * @return A description of this operand
     */
    String description() default "";

    /**
     * is this is default operand for module
     *
     * @return is this is default operand for module
     */
    boolean defaultOperand() default false;

    /**
     * Should help be displayed if this option is
     * present.
     *
     * @return True if this option is a help option
     */
    boolean helpRequest() default false;
}