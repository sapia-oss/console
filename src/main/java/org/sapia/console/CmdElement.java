package org.sapia.console;


/**
 * Abstract class for command line elements.
 *
 * @author Yanick Duchesne
 * 23-Dec-02
 */
public abstract class CmdElement {
  protected String _name;

  /**
   * Constructs an instance with given name.
   *
   * @param name a name.
   */
  protected CmdElement(String name) {
    _name = name;
  }

  /**
   * Returns this instance's name.
   *
   * @return this instance's name.
   */
  public String getName() {
    return _name;
  }
}
