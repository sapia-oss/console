package org.sapia.console;


/**
 * Models a command-line argument.
 *
 * @author Yanick Duchesne
 * 23-Dec-02
 */
public class Arg extends CmdElement {
  public Arg(String name) {
    super(name);
  }

  public String toString() {
    if (_name != null) {
      return _name;
    }

    return "";
  }
}
