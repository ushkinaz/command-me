/*
 * Copyright (c) 2010-2010, Dmitry Sidorenko. All Rights Reserved.
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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.commandme.samples;

import com.googlecode.commandme.CLIParser;
import com.googlecode.commandme.CliException;

/**
 *
 * @author ushkinaz
 */
public class HelloWorld {

    private String name;

    public void setName(String name){
        this.name = name;
    }

    public void greet(){
        System.out.println("Hello, " + name + "!");
    }

    public static void main(String[] argv) throws CliException{
        CLIParser.parseArguments(argv).execute(HelloWorld.class);
    }
}
