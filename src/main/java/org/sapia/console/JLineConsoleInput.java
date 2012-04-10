package org.sapia.console;

import java.io.IOException;

import jline.ConsoleReader;

public final class JLineConsoleInput implements ConsoleInput {
	
	private ConsoleReader reader;
	
	private JLineConsoleInput() throws IOException {
		reader = new ConsoleReader();
  }
	
	@Override
	public String readLine() throws IOException {
	  return reader.readLine();
	}
	
	@Override
	public char[] readPassword() throws IOException {
	  String pwd = reader.readLine(new Character('*'));
	  if(pwd != null) {
	  	return pwd.toCharArray();
	  }
	  return null;
	}
	
	public static JLineConsoleInput newInstance() throws IOException {
		return new JLineConsoleInput();
	}

}
