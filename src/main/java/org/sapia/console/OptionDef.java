package org.sapia.console;


/**
 * Describes a command-line option.
 * 
 * @author yduchesne
 *
 */
public final class OptionDef implements Comparable<OptionDef> {
  
  String  name;
  String  valueName = "";
  String  description = "";
  boolean mustHaveValue, required;
  
  /**
   * @param name the option name.
   * @param mustHaveValue if <code>true</code>, indicates that a value must be provided.
   */
  public OptionDef(String name, boolean mustHaveValue) {
    this.name = name;
    this.mustHaveValue = mustHaveValue;
  }
  
  /**
   * @param name the option name.
   * @param mustHaveValue if <code>true</code>, indicates that a value must be provided.
   * @param required if <code>true</code>, indicates that the option is required, but that it may no require
   * a value (if <code>mustHaveValue</code> is <code>true</code>, then this flag is assumed to be set to
   * <code>true</code> also.
   */
  public OptionDef(String name, boolean mustHaveValue, boolean required) {
    this.name = name;
    this.mustHaveValue = mustHaveValue;
    this.required = required;
  }
  
  
  /**
   * @return the name of this instance's corresponding option.
   */
  public String getName() {
    return name;
  }
  
  /**
   * This method returns the name of assign to the value argument, when displaying help,
   * such as in:
   * <pre>
   * - myoption <myValueName>
   * </pre>
   * <p>
   * This should be taken into account only when {@link #mustHaveValue()} returns <code>true</code>.
   * 
   * @return the name of the value to use when displaying help.
   */
  public String getValueName() {
    return valueName;
  }
  
  /**
   * @return the description of this instance's corresponding option.
   */
  public String getDescription() {
    return description;
  }
  
  /**
   * @return <code>true</code> if the option corresponding to this instance 
   * must have a value.
   */
  public boolean mustHaveValue() {
    return mustHaveValue;
  }
  
  /**
   * @return <code>true</code> if the option corresponding to this instance
   * is required.
   */
  public boolean required() {
    return required;
  }
  
  @Override
  public String toString() {
    return name;
  }
  
  @Override
  public int hashCode() {
    return name.hashCode();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj instanceof OptionDef) {
      OptionDef def = (OptionDef) obj;
      return name.equals(def.getName());
    }
    return false;
  }
  
  @Override
  public int compareTo(OptionDef o) {
    return name.compareTo(o.getName());
  }
  
}