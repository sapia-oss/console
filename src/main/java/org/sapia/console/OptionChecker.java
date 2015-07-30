package org.sapia.console;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sapia.console.OptionDef.Builder;

/**
 * An instance of this class can be used to validate options passed through a 
 * {@link CmdLine}.
 * 
 * @see Builder#buildChecker()
 * 
 * @author yduchesne
 *
 */
public class OptionChecker {

  private List<OptionDef>        defs;
  private Map<String, OptionDef> defsByOptionName;
  
  
  public OptionChecker(List<OptionDef> defs) {
    this.defs = defs;
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
   * @throws InputException if the given {@link CmdLine} is deemded invalid.
   */
  public void validate(CmdLine cmd) throws InputException {
    
    for (OptionDef d : defs) {
      if (d.required()) {
        cmd.assertOption(d.getName(), d.mustHaveValue());
      }
    }
    
  }
}
