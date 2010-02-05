package org.sapia.console.widgets;

import java.util.ArrayList;
import java.util.List;

import org.sapia.console.Console;

/**
 * An instance of this class is used to process user input in a wizard-like manner. Usage:
 * 
 * <pre>
 * 
 *   final Console console = // create console..
 *   final Person person = // some new person instance
 *   
 *   InputChain input =  console.input()
 *   
 *   .add("What is your first name?"
 *      new InputHandler(){
 *        public void onInput(Value val, Input from){
 *          if(val.isNull()){
 *            console.println("You must enter your name");
 *            from.display();
 *          }
 *          else{
 *            person.setName(val.get());
 *          }
 *        }
 *      }
 *   )
 *   .add("What is your age?"
 *      new InputHandler(){
 *        public void onInput(Value val, Input from){
 *          if(val.isNull()){
 *            console.println("You must enter your age");
 *            from.display();
 *          }
 *          else{
 *            try{
 *              person.setAge(val.asInt());
 *            }catch(NumberFormatException e){
 *              from.abort("Invalid age: " + val.get());
 *            }
 *          }
 *        }
 *      }
 *   )
 *   .display();
 *   
 *   if(input.isAborted()){
 *      console.println("Could not save data: " + input.abortMessage());
 *   }
 *   else{
 *     savePerson(person);
 *   }
 *   
 * </pre>
 * 
 * 
 * 
 * @author yduchesne
 *
 */
public class InputChain {
  
  private Console _console;
  private List<Input> _inputs = new ArrayList<Input>();
  private String _abortMsg;

  public InputChain(Console console){
    _console = console;
  }
  
  public InputChain add(String msg, InputHandler handler){
    _inputs.add(new Input(msg, _console, handler));
    return this;
  }
  
  public InputChain display(){
    for(Input in:_inputs){
      in.display();
      if(in.getAbortMsg() != null){
         _abortMsg = in.getAbortMsg();
         break;
      }
    }
    return this;
  }
  
  public boolean isAborted(){
    return _abortMsg != null;
  }
  
  public String abortMessage(){
    return _abortMsg;
  }
  
  public Console console(){
    return _console;
  }

}
