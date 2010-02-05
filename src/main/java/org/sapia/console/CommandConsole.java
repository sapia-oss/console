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
 * 29-Nov-02
 */
public class CommandConsole extends Console {
  private CommandFactory  _fac;
  private ConsoleListener _listener = new ConsoleListenerImpl();

  /**
   * Creates an instance of this class with the given factory.
   *
   * @param fac A <code>CommandFactory</code>.
   */
  public CommandConsole(CommandFactory fac) {
    super();
    _fac = fac;
  }

  /**
   * Creates an instance of this class with the given factory. The
   * input and output streams passed in are used internally for
   * command-line reading and display output respectively.
   *
   * @param a <code>CommandFactory</code>.
   * @param in The input stream of the console.
   * @param out The output stream of the console.
   * @param fac A <code>CommandFactory</code>.
>>>>>>> 1.6
   */
  public CommandConsole(InputStream in, OutputStream out, CommandFactory fac) {
    super(in, out);
    _fac = fac;
  }

  /**
   * Sets this instance's command listener.
   */
  public void setCommandListener(ConsoleListener listener) {
    _listener = listener;
  }

  /**
   * Starts this console in the current thread and loops indefinily on
   * input/display until an <code>AbortException</code> is thrown by  a
   * <code>Command</code> instance.
   */
  public void start() {
    int    idx  = 0;
    String line;
    String name = null;
    String args = null;
    _listener.onStart(this);

    while (true) {
      try {
        prompt();
        line = readLine();

        if (line.length() == 0) {
          continue;
        } else {
          CmdLine cmdLine = CmdLine.parse(line);

          if (cmdLine.size() == 0) {
            continue;
          }

          if (cmdLine.isNextArg()) {
            Command cmd = _fac.getCommandFor(name = cmdLine.chopArg().getName());
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
        _listener.onAbort(this);

        break;
      } catch (IOException e) {
        e.printStackTrace();

        break;
      } catch (CommandNotFoundException e) {
        _listener.onCommandNotFound(this, name);
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

  //  public static void main(String[] args) {
  //    try{
  //      ReflectCommandFactory fac = new ReflectCommandFactory();
  //      fac.addPackage("org.sapia.console");
  //      CommandConsole cons = new CommandConsole(fac);
  //      cons.start();
  //
  //    }catch(Throwable t){
  //      t.printStackTrace();
  //    }
  //  }  
}
