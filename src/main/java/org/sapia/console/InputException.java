package org.sapia.console;


/**
 * This exection should be thrown by commands when a the user as entered
 * invalid input.
 *
 * @author Yanick Duchesne
 */
public class InputException extends RuntimeException {
 
	static final long serialVersionUID = 1L;
	
	public InputException(String msg) {
    super(msg);
  }
}
