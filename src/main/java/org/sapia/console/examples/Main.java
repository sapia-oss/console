package org.sapia.console.examples;

import java.io.File;
import java.io.PrintStream;


/**
 * @author Yanick Duchesne
 *
 * <dl>
 * <dt><b>Copyright:</b><dd>Copyright &#169; 2002-2004 <a href="http://www.sapia-oss.org">Sapia Open Source Software</a>. All Rights Reserved.</dd></dt>
 * <dt><b>License:</b><dd>Read the license.txt file of the jar or visit the
 *        <a href="http://www.sapia-oss.org/license.html">license page</a> at the Sapia OSS web site</dd></dt>
 * </dl>
 */
public class Main {
  public static void main(String[] args) {
    try {
      java.io.FileOutputStream fos = new java.io.FileOutputStream(new File(
            "bin/output.txt"), true);
      PrintStream              ps = new PrintStream(fos, true);
      ps.println("THIS IS PROGRAM OUTPUT");
      ps.flush();
      ps.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
