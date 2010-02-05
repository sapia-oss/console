package org.sapia.console.widgets;

import java.util.ArrayList;
import java.util.List;

import org.sapia.console.Console;
import org.sapia.console.Text;

/**
 * An instance of this class creates a box around content, like so:
 * 
 * <pre>
 *   ***********************
 *   *                     *
 *   * this is content     *
 *   *                     *
 *   ***********************
 * </pre>
 * 
 * @author yduchesne
 *
 */
public class Box {
  
  private Console _console;
  private List<String> _lines = new ArrayList<String>();
  private boolean _centered;
  private boolean _bordered = true;
  private char _verticalChar = '*';
  private char _horizontalChar = '*';
  
  public Box(Console console){
    _console = console;
  }

  /**
   * @return the {@link Console} to which this instance writes.
   */
  public Console console(){
    return _console;
  }  
  
  /**
   * @param c the character used to draw the vertical lines of this box.
   * @return this instance.
   */
  public Box setVerticalChar(char c){
    _verticalChar = c;
    return this;
  }
  
  /**
   * @param c the character used to draw the horizontal lines of this box.
   * @return this instance.
   */
  public Box setHorizontalChar(char c){
    _horizontalChar = c;
    return this;
  }  
  
  /**
   * Flags this instance has having centered content (this method 
   * should be called before {@link #display()}.
   * 
   * @return this instance
   */
  public Box center(){
    _centered = true;
    return this;
  }
  
  /**
   * Flags this instance has having borderless content.
   * 
   * @return this instance.
   */
  public Box noBorder(){
    _bordered = false;
    return this;
  }
  
  /**
   * Prints an empty line.
   * 
   * @return this instance.
   */
  public Box println(){
    return println("");
  }
  
  /**
   * Prints the given line of content.
   * 
   * @param line
   * @return this instance.
   */
  public Box println(String line){
    _lines.add(line);
    return this;
  }  
  
  /**
   * Prints the given text.
   * 
   * @param txt
   * @return this instance.
   */
  public Box print(Text txt){
    for(String line:txt.getContent()){
      println(line);
    }
    return this;
  }
  
  /**
   * Displays this instance's content with a box.
   */
  public void display(){
    doDisplay(_centered);
  }
  
  private void doDisplay(boolean center){
    if(_bordered){
      _console.repeat(_horizontalChar);
      tips();
    }
    else{
      _console.println();
    }
    for(String line: _lines){
      if(_bordered){
        _console.print(_verticalChar + " ");
      }
      if(center){
        int margin = (_console.getWidth() - 2 - line.length()) / 2;
        if(margin > 0){
          StringBuffer buf = new StringBuffer();
          for(int i = 0; i < margin; i++){
            buf.append(" ");
          }
          line = buf.append(line).toString();        
        }
      }
      _console.print(line);
      int spaces = _console.getWidth() - line.length() - 2;
      if(spaces > 0){
        for(int i = 2; i < spaces; i++){
          _console.print(" ");
        }
      }
      if(_bordered){
        _console.println(" " + _verticalChar);
      }
      else{
        _console.println();
      }
    }
    if(_bordered){
      tips();
      _console.repeat(_horizontalChar);
    }
    else{
      _console.println();
    }
  }  
  
  private void tips(){
    _console.print(""+_verticalChar);
    for(int i = 1; i < _console.getWidth() - 1; i++){
      _console.print(" ");
    }
    _console.println(""+_verticalChar);
  }
}
