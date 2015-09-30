package org.sapia.console;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * Models a command-line option.
 *
 * @see org.sapia.console.CmdLine
 * 
 * @author yduchesne
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
   * @return this instance's value, split into multiple tokens
   */
  public String[] getSplitValue() {
    return _value == null ? new String[]{} : _value.split(",");
  }
  
  /**
   * @return this instance's value, split into multiple tokens - if this instance
   * has no value, the returned array will be empty.
   */
  public String[] getSplitValue(char delim) {
    if (_value == null) {
      return new String[]{};
    }
    List<String> tokens = new ArrayList<String>();
    StringTokenizer tk = new StringTokenizer(_value, "" + delim);
    while (tk.hasMoreTokens()) {
      tokens.add(tk.nextToken());
    }
    return tokens.toArray(new String[tokens.size()]);
  }
  
  /**
   * @return this option's value.
   * @throws InputException if no such value could be found.
   */
  public String getValueNotNull() {
    if (_value == null) {
      throw new InputException("No value found for option: '" + _name + "'");
    }
    return _value;
  }
  
  /**
   * @param defaultVal a default value to return if this instance has no value.
   * @return this instance's value, or the given value if this instance has no value.
   */
  public String getValueOrDefault(String defaultVal) {
    if (_value == null) {
      return defaultVal;
    }
    return _value;
  }
  
  /**
   * @param defaultVal a default value to return if this instance has no value.
   * @return this instance's value, or the given value if this instance has no value.
   */
  public boolean getValueOrDefault(boolean defaultVal) {
    if (_value == null) {
      return defaultVal;
    }
    return asBoolean();    
  }
  
  /**
   * @param defaultVal a default value to return if this instance has no value.
   * @return this instance's value, or the given value if this instance has no value.
   * @throws InputException if this instance's value could not be converted to an <code>int</code>.
   */
  public int getValueOrDefault(int defaultVal) throws InputException {
    if (_value == null) {
      return defaultVal;
    }
    return asInt();
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
      throw new InputException("Integer expected for option '" + getName() + "'");
    }

    try {
      return Integer.parseInt(_value);
    } catch (NumberFormatException e) {
      throw new InputException("Integer expected for option '" + getName() + "'");
    }
  }
  
  /**
   * @return <code>false</code> if this instance has no value.
   */
  public boolean isNull() {
    return _value == null;
  }
  
  /**
   * @return <code>true</code> of this instance has a value.
   */
  public boolean isSet() {
    return _value != null;
  }

  /**
   * @return <code>true</code> if this instance's value is not <code>null</code>, if it is "true", or if 
   * it starts with "y".
   */
  public boolean asBoolean() {
    if (_value == null) {
      return true;
    }
    return _value.equalsIgnoreCase("true") || _value.toLowerCase().startsWith("y");
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
