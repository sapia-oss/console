package org.sapia.console.examples;

import org.sapia.console.Console;
import org.sapia.console.Value;
import org.sapia.console.widgets.Input;
import org.sapia.console.widgets.InputHandler;
import org.sapia.console.widgets.Menu;
import org.sapia.console.widgets.MenuOptionHandler;

public class MenuConsole {
  
  static String firstName = null;
  static String lastName  = null;


  public static void main(String[] args) {
    Console cons = new Console();
    
    cons.underline("This is an example using various widgets", '=').println();
    
    cons.menu("Welcome. choose: ")
      .option("choice 1", 
          new MenuOptionHandler(){
            public void onChoose(Menu currentMenu) {
              currentMenu.create("Sub Menu 1")
                .option("Perform some input", 
                    new MenuOptionHandler(){
                      public void onChoose(final Menu currentMenu) {
                        
                        currentMenu.console().input()
                          .add("Enter your first name:", 
                              new InputHandler(){ public void onInput(Value val, Input from) {
                                if(val.isNull()){
                                  currentMenu.console().println("You must enter something");
                                  from.display();
                                }
                                else{
                                  firstName = val.get();
                                }
                              }  
                           })
                          .add("Enter your last name:", 
                              new InputHandler(){ public void onInput(Value val, Input from) {
                                if(val.isNull()){
                                  currentMenu.console().println("You must enter something");
                                  from.display();
                                }
                                else{
                                  lastName = val.get();
                                }
                              }  
                           })                           
                           .display();
                        currentMenu.console().println("Thank you " + firstName + " " + lastName);    
                        currentMenu.back();
                      }
                    
                    }
                )
              
              .display();
            }
          }
      )
      .option("choice 2 - display text in a box", 
          new MenuOptionHandler(){
            public void onChoose(Menu currentMenu) {
              currentMenu.console().box().println("Lorem ipsum dolor sit amet, consectetuer adipiscing elit.")
              .setHorizontalChar('-').setVerticalChar('|').center().display();
              currentMenu.display();
            }
          }
      )      
      .option("exit", 
          new MenuOptionHandler(){
            public void onChoose(Menu currentMenu) {
              currentMenu.back();
            }
          }
      )
      .display();
  }
}
