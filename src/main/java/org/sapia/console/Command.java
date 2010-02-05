package org.sapia.console;


/**
 * Models an executable console command.
 * <p>
 * [pattern: command]
 *
 * @author Yanick Duchesne
 * 29-Nov-02
 */
public interface Command {
  public void execute(Context ctx) throws AbortException, InputException;
}
