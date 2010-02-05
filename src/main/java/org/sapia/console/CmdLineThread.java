package org.sapia.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;


/**
 * An instance of this class can be used to invoke a command-line that spawns an external process.
 * <p>
 * Usage:
 * <pre>
 *    CmdLine cmd = new CmdLine();
 *    cmd.addArg("java");
 *    CmdLineThread th = new CmdLineThread(cmd);
 *    th.start();
 *    BufferedReader br = new BufferedReader(new InputStreamReader(th.getInputStream()));
 *    String line;
 *    try{
 *      while((line = br.readLine()) != null){
 *        System.out.println(line);
 *      }
 *    }finally{
 *      br.close();
 *    }
 * </pre>
 *
 * @author Yanick Duchesne
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2003 <a href="http://www.sapia-oss.org">Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *        <a href="http://www.sapia-oss.org/license.html">license page</a> at the Sapia OSS web site</dd></dt>
 * </dl>
 */
public class CmdLineThread extends Thread {
  private CmdLine          _cmd;
  private boolean          _ready;
  private boolean          _closed;
  private PipedInputStream _stdout;
  private PipedInputStream _stderr;
  private File             _procDir;
  private String[]         _env;
  private IOException      _exc;
  private Process          _proc;

  /**
   * Constructor for CmdLineThread.
   */
  public CmdLineThread(CmdLine cmd) {
    super("CmdLineThread");
    _cmd = cmd;
  }

  public CmdLineThread(CmdLine cmd, String[] env) {
    super("CmdLineThread");
    _cmd   = cmd;
    _env   = env;
  }

  public CmdLineThread(CmdLine cmd, File processDir, String[] env) {
    this(cmd);
    _procDir   = processDir;
    _env       = env;
  }

  public Process getProcess() {
    return _proc;
  }

  /**
   * Returns the stream that corresponds to the started process' stdout.
   *
   * @return an <code>InputStream</code>
   */
  public synchronized InputStream getInputStream() throws IOException {
    if (_exc != null) {
      throw _exc;
    }

    while (!_ready) {
      try {
        wait();
      } catch (InterruptedException e) {
        throw new IOException(e.getMessage());
      }
    }

    return new ProcessInputStream(_stdout, this);
  }

  /**
   * Returns the stream that corresponds to the started process' stderr.
   *
   * @return an <code>InputStream</code>
   */
  public synchronized InputStream getErrStream() throws IOException {
    if (_exc != null) {
      throw _exc;
    }

    while (!_ready) {
      try {
        wait();
      } catch (InterruptedException e) {
        throw new IOException(e.getMessage());
      }
    }

    return new ProcessInputStream(_stderr, this);
  }

  synchronized void close() {
    _closed = true;
  }

  synchronized boolean isClosed() {
    return _closed;
  }

  boolean hasException() {
    return _exc != null;
  }

  IOException getException() {
    return _exc;
  }

  /**
   * @see java.lang.Thread#run()
   */
  public void run() {
    PipedOutputStream perr = null;
    PipedOutputStream pout = null;

    try {
      Process proc;

      if (_procDir == null) {
        proc = Runtime.getRuntime().exec(_cmd.toArray(), _env);
      } else {
        proc = Runtime.getRuntime().exec(_cmd.toArray(), _env, _procDir);
      }

      _proc = proc;

      InputStream stderr = proc.getErrorStream();
      InputStream stdout = proc.getInputStream();
      _stdout   = new PipedInputStream();
      _stderr   = new PipedInputStream();
      pout      = new PipedOutputStream(_stdout);
      perr      = new PipedOutputStream(_stderr);

      streamsCreated();

      int    avail;
      byte[] buf;

      while (!isClosed()) {
        if ((avail = stderr.available()) > 0) {
          stderr.read(buf = new byte[avail]);
          perr.write(buf);
          perr.flush();
        }

        if ((avail = stdout.available()) > 0) {
          stdout.read(buf = new byte[avail]);
          pout.write(buf);
          pout.flush();
        }

        Thread.sleep(200);
      }
    } catch (IOException e) {
      _exc = e;
      streamsCreated();
    } catch (InterruptedException e) {
      _exc = new IOException(e.getMessage());
      streamsCreated();
    } finally {
      if (perr != null) {
        try {
          perr.close();
        } catch (IOException e) {
        }
      }

      if (pout != null) {
        try {
          pout.close();
        } catch (IOException e) {
        }
      }
    }
  }

  private synchronized void streamsCreated() {
    _ready = true;
    notify();
  }

  public static void main(String[] args) {
    try {
      String  cmdStr = "\"/opt/IBMJava2-141/bin/../bin/../jre/bin/java\" -Xms32M -Dcorus.process.java.main=org.sapia.corus.examples.NoopApplication -Dcorus.server.host=127.0.0.1 -Dcorus.server.port=33000 -Dcorus.server.domain=yanick -Dcorus.distribution.name=demo -Dcorus.distribution.version=1.0 -Dcorus.process.dir\"/opt/dev/java/sapia/corus/deploy/yanick_33000/demo/1.0/processes/10829977791640\" -Dcorus.process.id=10829977791640 -Dcorus.process.poll.interval=10 -Dcorus.process.status.interval=30 -Dcorus.process.profile=test -Duser.dir=/opt/dev/java/sapia/corus/deploy/yanick_33000/demo/1.0/common -cp /opt/dev/java/sapia/corus/lib/sapia_corus_starter.jar:/opt/dev/java/sapia/corus/dist/sapia_corus_starter.jar:/opt/dev/java/sapia/corus/deploy/yanick_33000/demo/1.0/common/lib/demo.jar:/opt/dev/java/sapia/corus/deploy/yanick_33000/demo/1.0/common/jdom-1_0b9.jar:/opt/dev/java/sapia/corus/deploy/yanick_33000/demo/1.0/common/lib/log4j-1_2_5.jar:/opt/dev/java/sapia/corus/deploy/yanick_33000/demo/1.0/common/lib/Piccolo-1_0_3.jar:/opt/dev/java/sapia/corus/deploy/yanick_33000/demo/1.0/common/lib/sapia_corus_iop.jar:/opt/dev/java/sapia/corus/deploy/yanick_33000/demo/1.0/common/lib/sapia_utils-1_2.jar org.sapia.corus.starter.Starter";
      CmdLine cmd = CmdLine.parse(cmdStr);

      /*
      CmdLine cmd = new CmdLine();
      cmd.addArg("java");
      */
      ExecHandle     handle = cmd.exec();

      BufferedReader br   = new BufferedReader(new InputStreamReader(
            handle.getInputStream()));
      String         line;

      while ((line = br.readLine()) != null) {
        System.out.println(line);
      }

      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*////////////////////////////////////////////////////////////////////
                            INNER CLASSES
  ////////////////////////////////////////////////////////////////////*/
  static final class ProcessInputStream extends InputStream {
    private InputStream   _is;
    private CmdLineThread _cmdThread;

    ProcessInputStream(InputStream is, CmdLineThread cmdThread) {
      _is          = is;
      _cmdThread   = cmdThread;
    }

    /**
     * @see java.io.InputStream#available()
     */
    public int available() throws IOException {
      checkException();

      return _is.available();
    }

    /**
     * @see java.io.InputStream#close()
     */
    public void close() throws IOException {
      checkException();

      try {
        if (_is != null) {
          _is.close();
        }
      } finally {
        _cmdThread.close();
      }
    }

    /**
     * @see java.io.InputStream#mark(int)
     */
    public synchronized void mark(int arg0) {
      _is.mark(arg0);
    }

    /**
     * @see java.io.InputStream#markSupported()
     */
    public boolean markSupported() {
      return _is.markSupported();
    }

    /**
     * @see java.io.InputStream#read()
     */
    public int read() throws IOException {
      checkException();

      return _is.read();
    }

    /**
     * @see java.io.InputStream#read(byte[], int, int)
     */
    public int read(byte[] arg0, int arg1, int arg2) throws IOException {
      checkException();

      return _is.read(arg0, arg1, arg2);
    }

    /**
     * @see java.io.InputStream#read(byte[])
     */
    public int read(byte[] arg0) throws IOException {
      checkException();

      return _is.read(arg0);
    }

    /**
     * @see java.io.InputStream#reset()
     */
    public synchronized void reset() throws IOException {
      checkException();
      _is.reset();
    }

    /**
     * @see java.io.InputStream#skip(long)
     */
    public long skip(long arg0) throws IOException {
      checkException();

      return _is.skip(arg0);
    }

    private void checkException() throws IOException {
      if (_cmdThread.hasException()) {
        throw _cmdThread.getException();
      }
    }
  }
}
