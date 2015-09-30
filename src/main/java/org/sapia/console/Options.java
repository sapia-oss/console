package org.sapia.console;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sapia.console.table.Row;
import org.sapia.console.table.Table;

/**
 * An instance of this class can be used to validate options passed through a 
 * {@link CmdLine}, or to display option help.
 * 
 * @see #validate(CmdLine)
 * @see Options#displayHelp(String, Console)
 * @see Options#displayHelp(String, ConsoleOutput, int)
 * @see Options#displayHelp(String, PrintStream)
 * @see Options#displayHelp(String, PrintStream, int)
 * 
 * @see Builder#buildOptions()
 * 
 * @author yduchesne
 *
 */
public class Options {
  
  /**
   * A builder used to build one to many {@link OptionDef}.
   * 
   * @author yduchesne
   *
   */
  public static final class Builder {
    
    private List<OptionDef> built = new ArrayList<OptionDef>();
    
    private String name, valueName;
    private StringBuilder description = new StringBuilder();
    private boolean required, mustHaveValue, displayArg, ignoreUnknowOptions;
    private int optionCount = 0;
    
    /**
     * @param name the name of the option.
     * @return this instance.
     */
    public Builder name(String name) {
      this.name = name;
      return this;
    }
    
    /**
     * @param name the name of the option's value in the online help 
     * (will be used if {@link #mustHaveValue()} has been invoked)
     * @return this instance.
     */
    public Builder valueName(String name) {
      this.valueName = name;
      return this;
    }

    /**
     * @param description some description content to append to the option's description.
     * @return this instance.
     */
    public Builder desc(String description) {
      this.description.append(description);
      return this;
    }
    
    /**
     * Indicates if this option is required.
     * 
     * @return this instance.
     */
    public Builder required() {
      required = true;
      return this;
    }
    
    /**
     * Indicates if the option is optional.
     * 
     * @return this instance.
     */
    public Builder optional() {
      required = false;
      return this;
    }

    /**
     * Indicates if the option must have a value, when it is specified.
     * 
     * @return this instance.
     */
    public Builder mustHaveValue() {
      mustHaveValue = true;
      return this;
    }
    
    /**
     * Indicates if only help will display the value name corresponding to the option.
     * 
     * @return this instance.
     * 
     * @see #valueName(String)
     */
    public Builder displayValueName() {
      displayArg = true;
      return this;
    }
    
    /**
     * Internally creates the {@link OptionDef} whose configuration was just set and adds it to this instance's
     * list (performs internall validation beforehand).
     * 
     * @return this instance
     */
    public Builder option() {
      if (name == null && optionCount > 0) {
        throw new IllegalArgumentException("Option name must be provided");
      }
      if (valueName == null) {
        valueName = "value";
      }
      if (name != null) {
        OptionDef opt = new OptionDef(name, mustHaveValue, required);
        opt.description = description.toString();
        opt.valueName   = valueName;
        built.add(opt);
        optionCount++;
      }
      reset();
      
      return this;
    }
    
    /**
     * Indicates that no error should be thrown when encountering unknown options.
     * 
     * @return this instance.
     */
    public Builder ignoreUnknownOptions() {
      ignoreUnknowOptions = true;
      return this;
    }
    
    /**
     * @return the {@link List} of {@link OptionDef} instances that were built thus far.
     */
    public List<OptionDef> build() {
      if (name != null) {
        option();
      }
      List<OptionDef> toReturn = built;
      built = new ArrayList<OptionDef>();
      return toReturn;
    }
    
    /**
     * @return an {@link Options} holding the {@link List} of {@link OptionDef}s that
     * were built.
     * 
     * @see #build()
     */
    public Options buildOptions() {
      return new Options(build(), displayArg, ignoreUnknowOptions);
    }
    
    /**
     * @return a new instance of this class.
     */
    public static Builder newInstance() {
      return new Builder();
    }
    
    private void reset() {
      name          = null;
      description   = new StringBuilder();
      mustHaveValue = false;
      required      = false;      
      optionCount   = 0;
    }
    
  }
  
  // ==========================================================================

  public static final int DEFAULT_TERMINAL_WIDTH = 80;
  
  private static final int NUM_COLUMNS = 3;
  
  private static final int COL_OPT_NAME = 0;
  
  private static final int COL_OPT_REQUIRED = 1;
  
  private static final int COL_OPT_DESC = 2;
  
  private List<OptionDef>        defs;
  private Map<String, OptionDef> defsByOptionName;
  private boolean                displayArg, ignoreUnknownOptions;
  
  public Options(List<OptionDef> defs, boolean displayArg, boolean ignoreUnknownOptions) {
    this.defs       = defs;
    this.displayArg = displayArg;
    this.ignoreUnknownOptions = ignoreUnknownOptions;
    defsByOptionName = new HashMap<String, OptionDef>();
    for (OptionDef d : defs) {
      defsByOptionName.put(d.getName(), d);
    }
  }
  
  /**
   * Checks the options of the given {@link CmdLine}, insuring that these match
   * the requirements has defined to this instance.
   * 
   * @param cmd a {@link CmdLine} instance to validate.
   * @throws InputException if the given {@link CmdLine} is deemed invalid.
   */
  public void validate(CmdLine cmd) throws InputException {
    if (!ignoreUnknownOptions) {
      for (Option o : cmd.getOptions()) {
        if (!defsByOptionName.containsKey(o.getName())) {
          throw new InputException("Unknown command-line option specified: " + o.getName());
        }
      }
    }
    
    for (OptionDef d : defs) {
      if (d.required()) {
        cmd.assertOption(d.getName(), d.mustHaveValue());
      } else if (d.mustHaveValue()) {
        if (cmd.containsOption(d.getName(), false)) {
          cmd.assertOption(d.getName(), true);
        }
      }
    }
  }
  
  /**
   * @param caption a caption to display before displaying option help itself (ignored if null or empty).
   * @param console the {@link Console} to display to.
   */
  public void displayHelp(String caption, Console console) {
    displayHelp(caption, console.out(), console.getWidth());
  }
  
  /**
   * @param caption a caption to display before displaying option help itself (ignored if null or empty).
   * @param out the {@link ConsoleOutput} to output to.
   * @param terminalWidth a terminal width.
   */
  public void displayHelp(String caption, ConsoleOutput out, int terminalWidth) {
    Table table = new Table(out, NUM_COLUMNS, (int) (terminalWidth / NUM_COLUMNS));
    
    int optNameWidth = (int) (terminalWidth * 0.35);
    int optReqWidth  = (int) (terminalWidth * 0.12);
    int optDescWidth = terminalWidth - (optNameWidth + optReqWidth);
    table.getTableMetaData().getColumnMetaDataAt(COL_OPT_NAME).setWidth(optNameWidth - 2);
    table.getTableMetaData().getColumnMetaDataAt(COL_OPT_REQUIRED).setWidth(optReqWidth - 1);
    table.getTableMetaData().getColumnMetaDataAt(COL_OPT_DESC).setWidth(optDescWidth -2);
    
    if (caption != null && caption.length() > 0) {
      out.println(caption);
      out.println();
    }
    
    for (OptionDef d : this.defs) {
      Row r = table.newRow();
      r.getCellAt(COL_OPT_NAME).append("-" + d.getName() + (d.mustHaveValue() && displayArg ? (" <" + d.getValueName() + ">") : ""));
      r.getCellAt(COL_OPT_REQUIRED).append(d.required() ? "required" : "optional");
      r.getCellAt(COL_OPT_DESC).append(d.getDescription());
      r.flush();
    }
  }

  /**
   * @param caption a caption to display before displaying option help itself (ignored if null or empty).
   * @param out the {@link PrintStream} to output to.
   * @param terminalWidth a terminal width.
   */
  public void displayHelp(String caption, PrintStream out, int terminalWidth) {
    displayHelp(caption, new ConsoleOutput.DefaultConsoleOutput(out), terminalWidth);
  }
  
  /**
   * @param caption a caption to display before displaying option help itself (ignored if null or empty).
   * @param out the {@link PrintStream} to output to.
   */
  public void displayHelp(String caption, PrintStream out) {
    displayHelp(caption, new ConsoleOutput.DefaultConsoleOutput(out), DEFAULT_TERMINAL_WIDTH);
  }
  
  /**
   * @param comparator the {@link Comparator} to use for sorting.
   * @return this instance.
   */
  public Options sort(Comparator<OptionDef> comparator) {
    this.defs.sort(comparator);
    return this;
  }
  
  /**
   * Internally sorts this instance's {@link OptionDef}s so that the required options appear first,
   * in alphabetical order.
   * 
   * @return this instance.
   */
  public Options sortAlphabeticallyRequiredFirst() {
    return sort(new Comparator<OptionDef>() {
      @Override
      public int compare(OptionDef o1, OptionDef o2) {
        if (o1.required() && !o2.required()) {
          return -1;
        } else if (!o1.required() && o2.required()) {
          return 1;
        } else {
          return o1.getName().compareTo(o2.getName());
        }
      }
    });
  }
}
