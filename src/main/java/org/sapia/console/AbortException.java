package org.sapia.console;


/**
 * Thrown by command to signals to the console that it should terminate.
 *
 * @author Yanick Duchesne
 */
public class AbortException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  
  public AbortException(String msg) {
    super(msg);
  }
  
  public AbortException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
