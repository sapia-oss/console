package org.sapia.console;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Abstracts console input source.
 * 
 * @author yduchesne
 *
 */
public interface ConsoleInput {
	
	/**
	 * Reads input from stdin.
	 * 
	 */
	public static final class DefaultConsoleInput implements ConsoleInput {
		
		private BufferedReader reader;
		
		private DefaultConsoleInput() throws IOException {
			reader = new BufferedReader(new InputStreamReader(System.in));
		}
		
		@Override
		public String readLine() throws IOException {
		  return reader.readLine();
		}
		
		@Override
		public char[] readPassword() throws IOException {
		  String pwd = readLine();
		  if (pwd != null) {
		  	return pwd.toCharArray();
		  }
		  return null;
		}
		
		public static final DefaultConsoleInput newInstance() {
			try {
				return new DefaultConsoleInput();
			} catch (IOException e) {
				throw new IllegalStateException("Could not create default console input");
			}
		}
	}
	
	// --------------------------------------------------------------------------
	
	/**
	 * @return the line read from the console.
	 * @throws IOException if an IO problem occurred while attempting to read a command line.
	 */
	public String readLine() throws IOException ;

	/**
	 * @return the password that was input.
	 * @throws IOException if an IO problem occurred while attempting to read the password.
	 */
	public char[] readPassword() throws IOException;
}
