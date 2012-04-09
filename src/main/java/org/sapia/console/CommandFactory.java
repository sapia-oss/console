package org.sapia.console;


/**
 * An interface that defines a creational method for {@link Command}
 * 
 * objects.
 *
 * @author Yanick Duchesne
 */
public interface CommandFactory {
	
	/**
	 * @param name the name of the {@link Command} to return.
	 * @return the {@link Command} corresponding to the given name.
	 * @throws CommandNotFoundException if no such command exists.
	 */
  public Command getCommandFor(String name) throws CommandNotFoundException;
}
