package org.sapia.console.examples;

import org.sapia.console.CmdLine;
import org.sapia.console.ExecHandle;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * @author Yanick Duchesne
 *
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2004 <a href="http://www.sapia-oss.org">Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *        <a href="http://www.sapia-oss.org/license.html">license page</a> at the Sapia OSS web site</dd></dt>
 * </dl>
 */
public class MainCaller {
  public static void main(String[] args) {
    try {
      CmdLine cmd = new CmdLine();
      cmd.addArg("sh")
         .addArg(System.getProperty("user.dir") + File.separator +
        "bin/callMain.sh").addArg(System.getProperty("java.home") +
        "/bin/java").addOpt("cp", "java/classes/").addArg("org.sapia.console.examples.Main");

      System.out.println("Invoking: " + cmd.toString());

      ExecHandle     handle = cmd.exec(new File(System.getProperty("user.dir")),
          null);
      BufferedReader reader = new BufferedReader(new InputStreamReader(
            handle.getInputStream()));
      String         pid = reader.readLine();
      System.out.println("process ID: " + pid);

      InputStream err   = handle.getErrStream();
      int         count = 0;

      while (err.available() <= 0) {
        Thread.sleep(200);
        count++;

        if (count == 2) {
          break;
        }
      }

      if (count == 2) {
        err.close();
      } else {
        int          bytes;
        byte[]       buf  = new byte[2000];
        StringBuffer data = new StringBuffer();

        while ((bytes = err.available()) > 0) {
          bytes = err.read(buf, 0, buf.length);
          data.append(new String(buf, 0, bytes));
        }

        err.close();

        if (data.length() > 0) {
          System.out.println("ERROR:");
          System.out.println(data.toString());
        }
      }

      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
