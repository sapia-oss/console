package org.sapia.console;


/**
 * Thrown when a command is not found.
 * 
 * @author Yanick Duchesne
 */
public class CommandNotFoundException extends Exception {
	
	static final long serialVersionUID = 1L;
	
  public CommandNotFoundException(String msg) {
    super(msg);
  }
}
