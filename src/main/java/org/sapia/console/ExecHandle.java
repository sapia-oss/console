package org.sapia.console;

import java.io.IOException;
import java.io.InputStream;


/**
 * Wraps a Java {@link Process} object.
 *
 * @see CmdLine
 * @see CmdLine#exec()
 * @see org.sapia.console.CmdLineThread
 *
 * @author Yanick Duchesne
 */
public class ExecHandle {
	
  private CmdLineThread _cmdThread;

  /**
   * Constructor for ExecHandle.
   */
  ExecHandle(CmdLineThread cmdThread) {
    _cmdThread = cmdThread;
  }

  /**
   * @see CmdLineThread#getInputStream()
   */
  public InputStream getInputStream() throws IOException {
    return _cmdThread.getInputStream();
  }

  /**
   * @see CmdLineThread#getErrStream()
   */
  public InputStream getErrStream() throws IOException {
    return _cmdThread.getErrStream();
  }

  /**
   * @return the <code>Process</code> held within this instance.
   */
  public Process getProcess() {
    return _cmdThread.getProcess();
  }

  /**
   *
   *
   */
  public void close() {
    _cmdThread.close();
  }
}
