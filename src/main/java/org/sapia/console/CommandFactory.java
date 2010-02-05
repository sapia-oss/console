package org.sapia.console;


/**
 * An interface that defines a creational method for <code>Command</code>
 * objects.
 *
 * @author Yanick Duchesne
 * 29-Nov-02
 */
public interface CommandFactory {
  public Command getCommandFor(String name) throws CommandNotFoundException;
}
