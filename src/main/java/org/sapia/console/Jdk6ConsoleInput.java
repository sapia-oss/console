package org.sapia.console;

import java.io.Console;

public final class Jdk6ConsoleInput implements ConsoleInput {
	
	private Console console;
	
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
	
	public static Jdk6ConsoleInput newInstance() {
		return new Jdk6ConsoleInput();
	}

}
