package org.sapia.console;


/**
 * A basic console message.
 * @author Yanick Duchesne
 * 29-Nov-02
 */
public class ConsoleListenerImpl implements ConsoleListener {
  /**
   * Displays "bye..." to the console.
   * @see org.sapia.console.ConsoleListener#onAbort(Console)
   */
  public void onAbort(Console cons) {
    cons.println("bye...");
  }

  /**
   * Displays "command not found" message.
   *
   * @see org.sapia.console.ConsoleListener#onCommandNotFound(Console, String)
   */
  public void onCommandNotFound(Console cons, String commandName) {
    cons.println("command not found: " + commandName);
  }

  /**
   * Empty implementation.
   *
   * @see org.sapia.console.ConsoleListener#onStart(Console)
   */
  public void onStart(Console cons) {
  }
}
