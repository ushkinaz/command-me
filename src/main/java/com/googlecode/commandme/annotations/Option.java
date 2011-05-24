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
 * Defines a parameter. This annotation should be placed on setter method of a parameter.
 * That method should have only one argument of primitive type and it should be public.
 *
 * @author Dmitry Sidorenko
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Option {
    /**
     * The long name of this parameter
     *
     * @return The long name of this parameter
     */
    String longName() default "";

    /**
     * The short name of this parameter
     *
     * @return The short name of this parameter
     */
    String shortName() default "";

    /**
     * A description of this parameter
     *
     * @return A description of this parameter
     */
    String description() default "";

    /**
     * Should help be displayed if this parameter is
     * present.
     *
     * @return True if this parameter is a help parameter
     */
    boolean helpRequest() default false;
}
