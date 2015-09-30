package org.sapia.console;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * Abstracts console output destination.
 * 
 * @author yduchesne
 *
 */
public interface ConsoleOutput {
  
	/**
	 * Writes output to stdout.
	 */
	public static final class DefaultConsoleOutput implements ConsoleOutput {
		
		private PrintWriter    writer;
		
		private DefaultConsoleOutput() {
			writer = new PrintWriter(System.out, true);
    }
		
		public DefaultConsoleOutput(PrintStream out) {
      writer = new PrintWriter(out, true);
    }

    public DefaultConsoleOutput(PrintWriter out) {
      writer = new PrintWriter(out, true);
    }
		
		@Override
		public void print(String s) {
			writer.print(s);
		}
		
		@Override
		public void println() {
		  writer.println(); 
		}
		
		@Override
		public void println(String s) {
			writer.println(s);
		}
		
		@Override
		public void print(char c) {
			writer.print(c);
		}
		
		@Override
		public void flush() {
			writer.flush();
		}
		
		public static final DefaultConsoleOutput newInstance() {
			return new DefaultConsoleOutput();
		}
	}	
	
	// --------------------------------------------------------------------------
	
	public void println(String s);
	
	public void println();
	
	public void print(String s);

	public void print(char c);
	
	public void flush();
	
}
