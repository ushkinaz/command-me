/**
 * Main package of the library.<br/>
 * Minimal "Hello World" application would look like this:
 *
 * <pre>
 * {@code
 * public class HelloWorld {
 *
 *   private String name;
 *
 *   public void setName(String name){
 *     this.name = name;
 *   }
 *
 *   public void greet(){
 *     System.out.println("Hello, " + name + "!");
 *   }
 *
 *   public static void main(String[] argv) throws CliException{
 *     CLIParser.parseArguments(argv).execute(HelloWorld.class);
 *   }
 * }
 *
 *
 * }
 * </pre>
 * Execution of the application:
 * <pre>
 * {@code $ java HelloWorld --name World greet
 * Hello, World!
 * }
 * </pre>
 *
 */
package com.googlecode.commandme;
