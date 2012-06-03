package org.sapia.console;


/**
 * Models a command execution context.
 *
 * @see CommandConsole#newContext()
 * @see Command
 * 
 * @author Yanick Duchesne
 */
public class Context {
  private Console _cons;
  private CmdLine _cmdLine;

  protected Context() {
  }

  protected void setUp(Console cons, CmdLine cmdLine) {
    _cons      = cons;
    _cmdLine   = cmdLine;
  }

  /**
   * Returns this context's console.
   *
   * @return this context's {@link Console}.
   */
  public Console getConsole() {
    return _cons;
  }

  /**
   * Returns this context's command-line object.
   *
   * @return this context's {@link Console}.
   */
  public CmdLine getCommandLine() {
    return _cmdLine;
  }
}
