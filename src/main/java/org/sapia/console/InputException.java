package org.sapia.console;


/**
 * This exection should be thrown by commands when a the user as entered
 * invalid input.
 *
 * @author Yanick Duchesne
 * 23-Dec-02
 */
public class InputException extends Exception {
  public InputException(String msg) {
    super(msg);
  }
}
