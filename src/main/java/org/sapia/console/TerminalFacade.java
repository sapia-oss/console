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
  public int getWidth();
  
  /**
   * @return the number of lines in the terminal.
   */
  public int getHeight();
}
