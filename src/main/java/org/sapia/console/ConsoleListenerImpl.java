package org.sapia.console;


/**
 * A basic {@link ConsoleListener} implementation.
 * 
 * @author Yanick Duchesne
 */
public class ConsoleListenerImpl implements ConsoleListener {
  /**
   * Displays "Bye..." to the console.
   * @see org.sapia.console.ConsoleListener#onAbort(Console)
   */
  public void onAbort(Console cons) {
    cons.println("Bye...");
  }

  /**
   * Displays "Command not found" message.
   *
   * @see org.sapia.console.ConsoleListener#onCommandNotFound(Console, String)
   */
  public void onCommandNotFound(Console cons, String commandName) {
    cons.println("Command not found: " + commandName);
  }

  /**
   * Empty implementation.
   *
   * @see org.sapia.console.ConsoleListener#onStart(Console)
   */
  public void onStart(Console cons) {
  }
}
