package org.sapia.console;

public class NullConsoleSession implements ConsoleSession{
  
  public void buffer(String line) {
  }
  
  public String down() {
    return null;
  }
  
  public String up() {
    return null;
  }

}
