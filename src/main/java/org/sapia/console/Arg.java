package org.sapia.console;


/**
 * Models a command-line argument.
 *
 * @author yduchesne
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
