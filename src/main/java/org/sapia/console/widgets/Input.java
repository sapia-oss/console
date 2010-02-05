package org.sapia.console.widgets;

import java.io.IOException;

import org.sapia.console.Console;
import org.sapia.console.Value;

public class Input {
  
  private String _msg;
  private InputHandler _handler;
  private Console _console;
  private String _abortMsg;
  
  Input(String msg, Console console, InputHandler handler){
    _msg = msg;
    _handler = handler;
    _console = console;
  }  
  public void display(){
    Value val = null;
    try{
      val = _console.enter(_msg);
    }catch(IOException e){
      throw new IllegalStateException("Could not acquire user input", e);
    }      
    _handler.onInput(val, this);
  }
  
  public void abort(String msg){
    _abortMsg = msg;
  }
  
  String getAbortMsg(){
    return _abortMsg;
  }

}
