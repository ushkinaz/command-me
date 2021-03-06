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

package com.googlecode.commandme;

/**
 * Command line interface
 *
 * @author Dmitry Sidorenko
 */
public interface CommandLine<T> {

    /**
     * Instantiates an instance of class T, fills it with options and calls appropriate methods
     *
     * @param arguments command line arguments to parse
     * @return instance of a class
     * @throws CliException an exception
     */
    T execute(String[] arguments) throws CliException;

    /**
     * Returns instance of a module
     *
     * @return module instance
     */
    T getModule();

    /**
     * Clears all internal references, making GC happy. It's not necessary to call in most cases.
     */
    void shutdown();
}
