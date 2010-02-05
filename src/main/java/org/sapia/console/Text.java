package org.sapia.console;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Encapsulates the lines of a text.
 * 
 * @author yduchesne
 *
 */
public class Text {
  
  private List<String> _lines = new ArrayList<String>();
  
  private byte[] CRLF = "\r\n".getBytes();
  
  private Console _console;
  
  public Text(Console console){
    _console = console;
  }
  
  /**
   * Gets the lines of text that this instance holds.
   * @return
   */
  public List<String> getContent(){
    return Collections.unmodifiableList(_lines);
  }

  /**
   * Writes the given line to this instance.
   */
  public Text write(String line){
    _lines.add(line);
    return this;
  }
  
  /**
   * Displays this instance's content to its console.
   * 
   * @return
   */
  public Text display(){
    for(String line:_lines){
      _console.println(line);
    } 
    return this;
  }
  
  /**
   * @return this instance's content as an {@link InputStream}
   * @throws IOException if an IO problem occurs.
   */
  public InputStream getInputStream() throws IOException{
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    for(String line:_lines){
      out.write(line.getBytes());
      out.write(CRLF);
    }
    return new ByteArrayInputStream(out.toByteArray());
  }

}
