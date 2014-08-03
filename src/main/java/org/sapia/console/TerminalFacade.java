package org.sapia.console;

/**
 * Abstracts the terminal.
 * 
 * @author yduchesne
 *
 */
public interface TerminalFacade {

  /**
   * @return the number of characters per line in the terminal.
   */
  public int getPreferredWidth();

}
