package org.sapia.console.examples;

import org.sapia.console.CommandConsole;


/**
 * @author Yanick Duchesne
 * 2-May-2003
 */
public class HelloWorldConsole extends CommandConsole {
  /**
   * Constructor for CustomConsole.
   */
  public HelloWorldConsole() {
    super(new HelloWorldFactory());
  }

  public static void main(String[] args) {
    HelloWorldConsole console = new HelloWorldConsole();
    console.start();
  }
}
