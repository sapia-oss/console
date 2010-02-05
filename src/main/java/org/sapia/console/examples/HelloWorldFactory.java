package org.sapia.console.examples;

import org.sapia.console.Command;
import org.sapia.console.CommandFactory;
import org.sapia.console.CommandNotFoundException;


/**
 * @author Yanick Duchesne
 * 2-May-2003
 */
public class HelloWorldFactory implements CommandFactory {
  /**
   * Constructor for HelloWorldFactory.
   */
  public HelloWorldFactory() {
    super();
  }

  /**
   * @see org.sapia.console.CommandFactory#getCommandFor(String)
   */
  public Command getCommandFor(String name) throws CommandNotFoundException {
    if (name.equalsIgnoreCase("hello")) {
      return new HelloWorldCommand();
    } else if (name.equalsIgnoreCase("quit") || name.equalsIgnoreCase("exit")) {
      return new QuitCommand();
    } else {
      throw new CommandNotFoundException(name);
    }
  }
}
