package org.sapia.console;

import java.io.IOException;

import jline.ConsoleReader;

/**
 * Wraps JLine's {@link ConsoleReader}.
 * 
 * @author yduchesne
 *
 */
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
	
	/**
	 * @return the {@link ConsoleReader} that this instance wraps.
	 */
	public ConsoleReader getConsoleReader() {
	  return reader;
	}
	
 /**
  * @return a new {@link TerminalFacade}.
  */
	public TerminalFacade getTerminal() {
	  return new TerminalFacade() {
      @Override
      public int getWidth() {
        return reader.getTermwidth();
      }
      
      @Override
      public int getHeight() {
        return reader.getTermheight();
      }
    };
	}
	
	public static JLineConsoleInput newInstance() throws IOException {
		return new JLineConsoleInput();
	}

}
