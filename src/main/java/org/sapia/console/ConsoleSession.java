package org.sapia.console;

public interface ConsoleSession {
  
  public void buffer(String line);
  public String up();
  public String down();
  
}
