package org.sapia.console;

import java.io.Console;

/**
 * A {@link ConsoleInput} based on the {@link Console} class introduced in JDK 6.
 * 
 * @author yduchesne
 *
 */
public final class Jdk6ConsoleInput implements ConsoleInput {
	
	private Console        console;
	private TerminalFacade terminal = new DefaultTerminalFacade();
	
	private Jdk6ConsoleInput() {
		console = System.console();
  }
	
	@Override
	public String readLine() {
	  return console.readLine();
	}
	
	@Override
	public char[] readPassword() {
		return console.readPassword();
	}
	
	@Override
	public TerminalFacade getTerminal() {
	  return terminal;
	}
	
	public static Jdk6ConsoleInput newInstance() {
		return new Jdk6ConsoleInput();
	}

}
