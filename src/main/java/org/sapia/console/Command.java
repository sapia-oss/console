package org.sapia.console;


/**
 * Models an executable console command.
 */
public interface Command {
  public void execute(Context ctx) throws AbortException, InputException;
}
