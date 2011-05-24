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

package com.googlecode.commandme.examples;

import com.googlecode.commandme.CLIParser;
import com.googlecode.commandme.annotations.Operand;
import com.googlecode.commandme.annotations.Option;

/**
 * Full command line: {@literal "HelloWorld --name "John Smith" -t 2 -h --money 100.0 greet bye"}<br/>
 * Minimal command line: {@literal "HelloWorld greet"}
 *
 * @author Dmitry Sidorenko
 */
public class HelloWorld {

    private String  name;
    private int     times;
    private boolean heartly;
    private double  money;

    @Operand
    public void greet() {
        System.out.println("Hello, " + name + "!");
        if (heartly) {
            System.out.println("We heartly welcome you");
        }
        if (times > 1) {
            System.out.println("We hug you " + times + " times!");
        }
    }

    @Operand
    public void bye() {
        System.out.println("Bye, " + name + "!");
        if (money > 0) {
            System.out.println("You own us $" + money + ". A bill will be sent shortly.");
        }
    }

    @Option
    public void setName(String name) {
        this.name = name;
    }

    @Option
    public void setTimes(int times) {
        this.times = times;
    }

    @Option()
    public void setHeartly(boolean heartly) {
        this.heartly = heartly;
    }

    @Option
    public void setMoney(double money) {
        this.money = money;
    }

    public static void main(String[] argv) {
        CLIParser.createModule(HelloWorld.class).execute(argv);
    }
}
