package org.sapia.console.examples;

import org.sapia.console.AbortException;
import org.sapia.console.Command;
import org.sapia.console.Context;
import org.sapia.console.InputException;


/**
 * @author Yanick Duchesne
 * 2-May-2003
 */
public class QuitCommand implements Command {
  /**
   * Constructor for QuitCommand.
   */
  public QuitCommand() {
    super();
  }

  /**
   * @see org.sapia.console.Command#execute(Context)
   */
  public void execute(Context ctx) throws AbortException, InputException {
    throw new AbortException("Quitting");
  }
}
