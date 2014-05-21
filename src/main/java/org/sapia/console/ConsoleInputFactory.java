package org.sapia.console;

import java.io.Console;
import java.io.IOException;

import jline.ConsoleReader;

/**
 * A factory of different {@link ConsoleInput} implementations.
 * 
 * @author yduchesne
 *
 */
public class ConsoleInputFactory {

	/**
	 * @return a {@link ConsoleInput} that wraps {@link System#in}.
	 */
	public static ConsoleInput createDefaultConsoleInput() {
		return ConsoleInput.DefaultConsoleInput.newInstance();
	}
	
	/**
	 * @return a {@link Jdk6ConsoleInput} that wraps the system {@link Console}.
	 */
	public static Jdk6ConsoleInput createJdk6ConsoleInput() {
		return Jdk6ConsoleInput.newInstance();
	}
	
	/**
	 * @return a {@link JLineConsoleInput} that wraps a JLine {@link ConsoleReader}.
	 * @throws IOException if an IO problem occurred while trying to create
	 * the {@link ConsoleInput}.
	 */
	public static JLineConsoleInput createJLineConsoleInput() throws IOException {
		return JLineConsoleInput.newInstance();
	}
	
}
