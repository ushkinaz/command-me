/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.googlecode.commandme.it;

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
