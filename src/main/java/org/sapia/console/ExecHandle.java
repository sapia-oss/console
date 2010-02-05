package org.sapia.console;

import java.io.IOException;
import java.io.InputStream;


/**
 * Wraps a Java <code>Process</code> object.
 *
 * @see CmdLine
 * @see CmdLine#exec()
 * @see org.sapia.console.CmdLineThread
 *
 * @author Yanick Duchesne
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2003 <a href="http://www.sapia-oss.org">Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *        <a href="http://www.sapia-oss.org/license.html">license page</a> at the Sapia OSS web site</dd></dt>
 * </dl>
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
