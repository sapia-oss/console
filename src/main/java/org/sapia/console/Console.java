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
  
  public interface ConsoleIO {
    
    /**
     * @return the {@link ConsoleInput} to use.
     */
    public ConsoleInput getInput();
    
    /**
     * @return the {@link ConsoleOutput} to use.
     */
    public ConsoleOutput getOutput();
  }

  
  public static final String DEFAULT_PROMPT = ">>";
  
  private ConsoleInput       in;
  private ConsoleOutput      out;
  private String             prompt 						 = DEFAULT_PROMPT;
  private boolean            emptyLineAfterInput = true; 
  private int 							 width;

  public Console(ConsoleIO io) {
    this(io.getInput(), io.getOutput());
  }
  
  public Console(ConsoleInput in, ConsoleOutput out) {
    this.in  = in;
    this.out = out;
    width = in.getTerminal().getPreferredWidth();
  }
  
  public Console() {
    this(ConsoleInput.DefaultConsoleInput.newInstance(), ConsoleOutput.DefaultConsoleOutput.newInstance());
  }
  
  /**
   * @param width the display width, in number of characters.
   */
  public void setWidth(int width){
    if(width < 0){
      throw new IllegalArgumentException("Width cannot be negative");
    }
    this.width = width;
  }
  
  /**
   * @return the display width, in number of characters
   */
  public int getWidth(){
    return width;
  }
    
  /**
   * @param lineAfterInput if <code>true</code>, indicates that an empty line should be displayed
   * automatically after user input (true by default).
   * @return this instance.
   */
  public Console setPrintEmptyLineAfterInput(boolean lineAfterInput) {
    emptyLineAfterInput = lineAfterInput;
    return this;
  }
  
  /**
   * @see #setPrintEmptyLineAfterInput(boolean)
   * @return
   */
  public boolean isEmptyLineAfterInput(){
    return emptyLineAfterInput;
  }

  /**
   * @return the {@link ConsoleOutput} that is internally used by this console
   * for output.
   */
  public ConsoleOutput out() {
    return out;
  }

  /**
   * @return the {@link ConsoleInput} that is internally used for input.
   */
  public ConsoleInput in() {
    return in;
  }

  /**
   * @param out the {@link ConsoleOutput} that is used for output.
   */
  public void setOut(ConsoleOutput out) {
    this.out = out;
  }

  /**
   * Prints the given data without CRLF at the end.
   * 
   * @param msg a message or content. 
   * @return this instance.
   */
  public Console print(String msg) {
    out.print(msg);
    out.flush();
    return this;
  }
  
  /**
   * Prints the given data with CRLF at the end.
   * 
   * @param msg a message or content. 
   * @return this instance.
   */
  public Console println(String msg) {
    out.println(msg);
    out.flush();
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
    if(msg.length() < width){
      int margin = (width - msg.length()) / 2;
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
    out.println();
    out.flush();
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
    if(emptyLineAfterInput){
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
    if(emptyLineAfterInput){
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
    out.print(prompt + " ");
    out.flush();
    return this;
  }

  /**
   * @param prompt sets the prompt to display.
   */
  public void setPrompt(String prompt) {
    this.prompt = prompt;
  }

  /**
   * @return the line of data that was read from this instance's input, 
   * or <code>null</code> if not such data was read.
   * @throws IOException
   */
  public String readLine() throws IOException {
    return in.readLine();
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
    return repeat(c, width);
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
      out.print(c);
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
  
  /**
   * @param t a {@link Throwable} instance, whose stacktrace should be 
   * redirected to this instance's {@link ConsoleOutput}.
   */
  public void printStackTrace(Throwable t) {
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    t.printStackTrace(new PrintStream(output, true));
    out.println(output.toString());
  }
  
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
