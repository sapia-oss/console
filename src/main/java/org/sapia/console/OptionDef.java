package org.sapia.console;

import java.util.ArrayList;
import java.util.List;

/**
 * Describes a command-line option.
 * 
 * @author yduchesne
 *
 */
public final class OptionDef implements Comparable<OptionDef> {
  
  /**
   * A builder used to build one to many {@link OptionDef}.
   * 
   * @author yduchesne
   *
   */
  public static final class Builder {
    
    private List<OptionDef> built = new ArrayList<OptionDef>();
    
    private String name, description;
    private boolean required, mustHaveValue;
    
    public Builder name(String name) {
      this.name = name;
      return this;
    }
    
    public Builder desc(String description) {
      this.description = description;
      return this;
    }
    
    public Builder required() {
      required = true;
      return this;
    }
    
    public Builder optional() {
      required = false;
      return this;
    }
    
    public Builder mustHaveValue() {
      mustHaveValue = true;
      return this;
    }
    
    public Builder option() {
      if (name == null) {
        throw new IllegalArgumentException("Option name must be provided");
      }
      if (description == null) {
        description = "";
      }
      OptionDef opt = new OptionDef(name, mustHaveValue, required);
      opt.description = description;
      built.add(opt);
      
      reset();
      
      return this;
    }
    
    public List<OptionDef> build() {
      reset();
      return built;
    }
    
    public OptionChecker buildChecker() {
      reset();
      return new OptionChecker(built);
    }
    
    public static Builder newInstance() {
      return new Builder();
    }
    
    private void reset() {
      name          = null;
      description   = null;
      mustHaveValue = false;
      required      = false;      
    }
    
  }
  
  // ==========================================================================
  
  private String  name;
  private String  description;
  private boolean mustHaveValue, required;
  
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
    this.required = mustHaveValue || required;
  }
  
  
  /**
   * @return the name of this instance's corresponding option.
   */
  public String getName() {
    return name;
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