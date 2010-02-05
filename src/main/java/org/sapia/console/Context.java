package org.sapia.console;


/**
 * Models a command execution context.
 *
 * @see CommandConsole#newContext()
 * @see Command
 * @author Yanick Duchesne
 * 29-Nov-02
 */
public class Context {
  private Console _cons;
  private CmdLine _cmdLine;

  protected Context() {
  }

  void setUp(Console cons, CmdLine cmdLine) {
    _cons      = cons;
    _cmdLine   = cmdLine;
  }

  /**
   * Returns this context's console.
   *
   * @return this context's <code>Console</code>
   */
  public Console getConsole() {
    return _cons;
  }

  /**
   * Returns this context's command-line object.
   *
   * @return this context's <code>Console</code>
   */
  public CmdLine getCommandLine() {
    return _cmdLine;
  }
}
