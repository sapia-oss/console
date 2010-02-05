package org.sapia.console;


/**
 * Models a command-line option.
 *
 * @see org.sapia.console.CmdLine
 * @author Yanick Duchesne
 * 23-Dec-02
 */
public class Option extends CmdElement {
  private String _value;

  /**
   * Creates and option with the given name.
   *
   * @param name the option name.
   */
  public Option(String name) {
    super(name);
  }

  /**
   * Creates and option with the given name and value.
   *
   * @param name the option name.
   * @param value the option value.
   */
  public Option(String name, String value) {
    super(name);
    _value = value;
  }

  /**
   * Returns this instance's value.
   *
   * @return this option's value as a string, or <code>null</code> if
   * no value was provided.
   */
  public String getValue() {
    return _value;
  }

  /**
   * Returns this option's value as an integer.
   *
   * @return this option's value as an <code>int</code>.
   *
   * @throws InputException if no value exists or if the value does
   * not evaluate to an integer.
   */
  public int asInt() throws InputException {
    if (_value == null) {
      throw new InputException("integer expected for option '" + getName() +
        "'");
    }

    try {
      return Integer.parseInt(_value);
    } catch (NumberFormatException e) {
      throw new InputException("integer expected for option '" + getName() +
        "'");
    }
  }

  /**
   * Returns this option's value as a boolean.
   *
   * @return this option's value as a <code>boolean</code>.
   *
   * @throws InputException if no value exists.
   */
  public boolean asBoolean() throws InputException {
    if (_value == null) {
      throw new InputException(
        "true/false, yes/no  or on/off expected for option '" + getName() +
        "'");
    }

    return _value.equals("true") || _value.equals("yes") ||
    _value.equals("on");
  }

  void setValue(String value) {
    _value = value;
  }

  public String toString() {
    if (_value != null) {
      return "-" + _name + " " + _value;
    } else {
      return "-" + _name;
    }
  }
}
