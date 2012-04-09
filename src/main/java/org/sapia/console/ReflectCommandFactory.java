package org.sapia.console;

import java.util.*;


/**
 * A command factory that creates {@link Command} instances
 * dynamically, by resolving the command name to a class. The command's
 * name has its first letter capitalized, and it is prepended with successive
 * package names; the first command class that is found for a given package has
 * an object instantiated from it.
 * <p>
 * If all packages have been visited and no command class was found
 * for the given command name, then a {@link CommandNotFoundException}
 * is thrown.
 *
 * @see #getCommandFor(String)
 * 
 * @author Yanick Duchesne
 */
public class ReflectCommandFactory implements CommandFactory {
	
  private List<String> _pckgs = new ArrayList<String>();

  /**
   * @see org.sapia.console.CommandFactory#getCommandFor(String)
   */
  public Command getCommandFor(String name) throws CommandNotFoundException {
    Command cmd = null;

    for (int i = 0; i < _pckgs.size(); i++) {
      try {
        cmd = (Command) Class.forName((String) _pckgs.get(i) + "." +
            firstToUpper(name)).newInstance();
      } catch (ClassNotFoundException e) {
        // noop      
      } catch (Throwable t) {
        throw new CommandNotFoundException(t + ": " + t.getMessage());
      }
    }

    if (cmd == null) {
      throw new CommandNotFoundException(name);
    }

    return cmd;
  }

  /**
   * Adds a package to this instance.
   */
  public ReflectCommandFactory addPackage(String name) {
    _pckgs.add(name);

    return this;
  }

  private String firstToUpper(String name) {
    StringBuffer buf = new StringBuffer();

    for (int i = 0; i < name.length(); i++) {
      if (i == 0) {
        buf.append(Character.toUpperCase(name.charAt(i)));
      } else {
        buf.append(name.charAt(i));
      }
    }

    return buf.toString();
  }
}
