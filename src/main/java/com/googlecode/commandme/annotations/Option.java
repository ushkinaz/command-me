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
 * Defines a option. This annotation should be placed on setter method of a option.
 * That method should have only one argument of primitive type and it should be public.
 *
 * @author Dmitry Sidorenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Option {
    /**
     * The long name of this option
     *
     * @return The long name of this option
     */
    String longName() default "";

    /**
     * The short name of this option
     *
     * @return The short name of this option
     */
    String shortName() default "";

    /**
     * A description of this option
     *
     * @return A description of this option
     */
    String description() default "";

    /**
     * Marks option as mandatory.
     *
     * @return {@code true} if this option is mandatory
     */
    boolean required() default false;
}
