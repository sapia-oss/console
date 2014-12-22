package org.sapia.console;

import java.io.*;


/**
 * A command-line console that can be embedded in applications. The
 * console parses command-lines to create <code>Command</code> instances
 * that it executes.
 * <p>
 * An instance of this class takes a <code>CommandFactory</code> at
 * construction, and delegates command object creation to it.
 * <p>
 * Embedding a command console is as shown below:
 * <p>
 * <pre>
 *   ReflectCommandFactory fac = new ReflectCommandFactory();
 *   fac.addPackage("org.mycommand.package");
 *   CommandConsole cons = new CommandConsole(fac);
 *   cons.start();
 * </pre>
 *
 * @author Yanick Duchesne
 */
public class CommandConsole extends Console {
  
  private CommandFactory  commandFactory;
  private ConsoleListener consoleListener = new ConsoleListenerImpl();
  
  /**
   * Creates an instance of this class with the given factory.
   *
   * @param fac a {@link CommandFactory}.
   */
  public CommandConsole(CommandFactory fac) {
    super();
    commandFactory = fac;
  }

  /**
   * Creates an instance of this class with the given factory. The
   * input and output streams passed in are used internally for
   * command-line reading and display output respectively.
   *
   * @param in the {@link ConsoleInput}.
   * @param out the {@link ConsoleOutput}.
   * @param fac a {@link CommandFactory}
   */
  public CommandConsole(ConsoleInput in, ConsoleOutput out, CommandFactory fac) {
    super(in, out);
    commandFactory = fac;
  }
  
  /**
   * Creates an instance of this class with the given factory. The
   * input and output streams passed via the given {@link ConsoleIO}  
   * are used internally for command-line reading and display output 
   * respectively.
   *
   * @param io the {@link ConsoleIO}.
   * @param fac a {@link CommandFactory}
   */
  public CommandConsole(ConsoleIO io, CommandFactory fac) {
    super(io);
    commandFactory = fac;
  }
  
  /**
   * @return this instance's {@link CommandFactory}.
   */
  public CommandFactory getCommandFactory() {
    return commandFactory;
  }

  /**
   * Sets this instance's command listener.
   */
  public void setCommandListener(ConsoleListener listener) {
    consoleListener = listener;
  }

  /**
   * Starts this console in the current thread and loops indefinitely on
   * input/display until an {@link AbortException} is thrown by  a
   * {@link Command} instance.
   */
  public void start() {
    String line;
    String name = null;
    consoleListener.onStart(this);

    while (true) {
      try {
        prompt();
        line = readLine();

        if (line == null) {
          consoleListener.onAbort(this);
          break;
        }
          
        if (line.length() == 0) {
          continue;
        } else {
          CmdLine cmdLine = CmdLine.parse(line);

          if (cmdLine.size() == 0) {
            continue;
          }

          if (cmdLine.isNextArg()) {
            Command cmd = commandFactory.getCommandFor(name = cmdLine.chopArg().getName());
            Context ctx = newContext();
            ctx.setUp(this, cmdLine);
            cmd.execute(ctx);
          } else {
            println("Command name expected");
          }
        }
      } catch (InputException e) {
        this.println(e.getMessage());
        
      } catch (AbortException e) {
        consoleListener.onAbort(this);

        break;
      } catch (IOException e) {
        e.printStackTrace();

        break;
      } catch (CommandNotFoundException e) {
        consoleListener.onCommandNotFound(this, name);
      }
    }
  }

  /**
   * Template method internally called by this instance to create
   * new <code>Context</code> instances.
   */
  protected Context newContext() {
    return new Context();
  }

}
