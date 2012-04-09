package org.sapia.console;

import java.io.*;

import org.sapia.console.widgets.Box;
import org.sapia.console.widgets.InputChain;
import org.sapia.console.widgets.Menu;


/**
 * An basic command-line console.
 *
 * @author Yanick Duchesne
 * 
 */
public class Console {
  public static final String DEFAULT_PROMPT = ">>";
  private ConsoleInput       _in;
  private ConsoleOutput      _out;
  private String             _prompt = DEFAULT_PROMPT;
  private ConsoleSession     _session = new NullConsoleSession();
  private boolean            _emptyLineAfterInput = true; 
  private int _width          = 80;

  public Console(ConsoleInput in, ConsoleOutput out) {
    _in    = in;
    _out   = out;
  }
  
  public Console() {
    this(ConsoleInput.DefaultConsoleInput.newInstance(), ConsoleOutput.DefaultConsoleOutput.newInstance());
  }
  
  public void setWidth(int width){
    if(width < 0){
      throw new IllegalArgumentException("Width cannot be negative");
    }
    _width = width;
  }
  
  public int getWidth(){
    return _width;
  }
  
  /**
   * @param session a {@link ConsoleSession}
   */
  public void setSession(ConsoleSession session){
    _session = session;
  }
  
  /**
   * @param lineAfterInput if <code>true</code>, indicates that an empty line should be displayed
   * automatically after user input (true by default).
   * @return this instance.
   */
  public Console setPrintEmptyLineAfterInput(boolean lineAfterInput) {
    _emptyLineAfterInput = lineAfterInput;
    return this;
  }
  
  /**
   * @see #setPrintEmptyLineAfterInput(boolean)
   * @return
   */
  public boolean isEmptyLineAfterInput(){
    return _emptyLineAfterInput;
  }

  /**
   * @return the {@link ConsoleOutput} that is internally used by this console
   * for output.
   */
  public ConsoleOutput out() {
    return _out;
  }

  /**
   * @return the {@link ConsoleInput} that is internally used for input.
   */
  public ConsoleInput in() {
    return _in;
  }

  /**
   * @param out the {@link ConsoleOutput} that is used for output.
   */
  public void setOut(ConsoleOutput out) {
    _out = out;
  }

  /**
   * Prints the given data without CRLF at the end.
   * 
   * @param msg a message or content. 
   * @return this instance.
   */
  public Console print(String msg) {
    _out.print(msg);
    _out.flush();
    return this;
  }
  
  /**
   * Prints the given data with CRLF at the end.
   * 
   * @param msg a message or content. 
   * @return this instance.
   */
  public Console println(String msg) {
    _out.println(msg);
    _out.flush();
    return this;
  }

  /**
   * Same as {@link #println(String)}, except it centers the content
   * based on this instance's width.
   * 
   * @param msg
   * @return this instance.
   * @see #setWidth(int) 
   */
  public Console center(String msg){
    if(msg.length() < _width){
      int margin = (_width - msg.length()) / 2;
      for(int i = 0; i < margin; i++){
        print(" ");
      }
      println(msg);
    }
    else{
      println(msg);
    }
    return this;
  }

  /**
   * Prints an empty line (with CRLF at the end).
   * 
   * @return this instance.
   */  
  public Console println() {
    _out.println();
    _out.flush();
    return this;
  }  

  /**
   * Waits for user input (takes a message):
   * 
   * <pre>
   * String name = console.input("What is your name ?");
   * </pre>
   * 
   * @param msg
   * @return
   * @throws IOException
   */
  public String input(String msg) throws IOException {
    prompt();
    
    if(msg != null && msg.length() > 0){
      print(msg + " ");
    }
    String input = readLine();
    if(input != null){
      _session.buffer(input);
    }
    if(_emptyLineAfterInput){
      println();
    }
    return input;
  }

  /**
   * Same as {@link #input(String)}, but encapsulates the input data
   * in a convenient {@link Value} instance.
   * 
   * @param msg
   * @return a {@link Value} that encapsulated the data that was input.
   * @throws IOException
   */
  public Value enter(String msg) throws IOException {
    prompt();
    if(msg != null && msg.length() > 0){
      print(msg + " ");
    }
    String input = readLine();
    if(input != null){
      _session.buffer(input);
    }
    if(_emptyLineAfterInput){
      println();
    }    
    return new Value(input);
  }  

  /**
   * Prints the prompt value to this instance's output.
   * 
   * @return this instance.
   */
  public Console prompt() {
    _out.print(_prompt + " ");
    _out.flush();
    return this;
  }

  public void setPrompt(String prompt) {
    _prompt = prompt;
  }

  /**
   * @return the line of data that was read from this instance's input, 
   * or <code>null</code> if not such data was read.
   * @throws IOException
   */
  public String readLine() throws IOException {
    return _in.readLine();
  }
  
  /**
   * Creates a {@link Menu} and returns it.
   * 
   * @param title the title of the menu to create.
   * @return the {@link Menu} that was created.
   */
  public Menu menu(String title){
    return new Menu(title, this);
  }
  
  /**
   * Creates a new {@link InputChain} and returns it.
   * 
   * @return a new {@link InputChain}.
   */
  public InputChain input(){
    return new InputChain(this);
  }

  /**
   * Creates a {@link Menu} and returns it.
   * 
   * @return the {@link Menu} that was created.
   */
  public Menu menu(){
    return new Menu(this);
  }
  
  /**
   * Displays the given character in a line the length
   * of this instance's width.
   *  
   * @param c a character.
   * @return this instance.
   */
  public Console repeat(char c){
    return repeat(c, _width);
  }
  
  /**
   * Displays the given character in a line of the given length.
   * 
   * @param c a character
   * @param len the number of times that the given character should be repeated.
   * @return this instance.
   */
  public Console repeat(char c, int len){
    for(int i = 0; i < len; i++){
      _out.print(c);
    }
    println();
    return this;
  }  
  
  /**
   * Displays the given text, with a given character repeated underneath it to
   * create an underline.
   *  
   * @param text some text
   * @param underlineChar the character to use when displaying the underline
   * @return this instance.
   */
  public Console underline(String text, char underlineChar){
    println(text);
    repeat(underlineChar, text.length());
    return this;
  }

  /*
  public Console splashAndCenter(String resource){
    return doSplash(resource, true);
  }  
  
  public Console splash(String resource){
    return doSplash(resource, false);
  }
  
  private Console doSplash(String resource, boolean center){
    InputStream is = getClass().getResourceAsStream(resource);
    if(is == null){
      is = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
    }
    if(is == null){
      throw new IllegalStateException("Could not find resource: " + resource);
    }
    try{
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = null;
      while((line = reader.readLine()) != null){
        if(center){
          center(line);
        }
        else{
          println(line);
        }
      }
    }catch(IOException e){
      throw new IllegalStateException("Could not read resource data: " + resource);      
    }finally{
      try{
        is.close();
      }catch(IOException e){}
    }
    return this;
  }*/
  
  /**
   * Searches for the given resource and returns its content in a string.
   * @param name the name of the resource.
   * @return the content of the resource.
   */
  public String getResourceContent(String name){
    InputStream is = getClass().getResourceAsStream(name);
    if(is == null){
      is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }
    if(is == null){
      throw new IllegalStateException("Could not find resource: " + name);
    }
    StringBuffer content = new StringBuffer();
    try{
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = null;
      while((line = reader.readLine()) != null){
        content.append(line).append("\r\n");
      }
      return content.toString();
    }catch(IOException e){
      throw new IllegalStateException("Could not read resource data: " + name);      
    }finally{
      try{
        is.close();
      }catch(IOException e){}
    }    
  }
  
  public Text getResourceText(String name){
    InputStream is = getClass().getResourceAsStream(name);
    if(is == null){
      is = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
    }
    if(is == null){
      throw new IllegalStateException("Could not find resource: " + name);
    }
    Text txt = new Text(this);
    try{
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      String line = null;
      while((line = reader.readLine()) != null){
        txt.write(line);
      }
      return txt;
    }catch(IOException e){
      throw new IllegalStateException("Could not read resource data: " + name);      
    }finally{
      try{
        is.close();
      }catch(IOException e){}
    }    
  }

  /**
   * Creates a box that will be displayed to this instance's output.
   * 
   * @return a new {@link Box}
   */
  public Box box(){
    return new Box(this);
  }

}
