package org.sapia.console;


/**
 * Listener that is called back by a <code>CommandConsole</code>
 * instance at various moments.
 *
 * @author Yanick Duchesne
 * 29-Nov-02
 */
public interface ConsoleListener {
  /**
   * Called when the console starts (can be implemented to display
   * welcome message).
   */
  public void onStart(Console cons);

  /**
   * Called when the console cannot interpret a given command (Can be
   * implemented to display an error message).
   */
  public void onCommandNotFound(Console cons, String commandName);

  /**
   * Called when the console exits (can be implemented to display a
   * good-bye message).
   */
  public void onAbort(Console cons);
}
