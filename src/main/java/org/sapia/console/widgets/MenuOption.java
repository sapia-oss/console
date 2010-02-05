package org.sapia.console.widgets;

import org.sapia.console.Console;

/**
 * This class models an option in a {@link Menu}
 * @author yduchesne
 *
 */
public class MenuOption {
  
  private int _index;
  private String _label;
  private MenuOptionHandler _handler;
  
  MenuOption(int index, String label, MenuOptionHandler handler){
    _index = index;    
    _label = label;
    _handler = handler;
  }
  
  void display(Console cons){
    cons.println(_index + ") " + _label);
  }
  
  void onChoose(Menu currentMenu){
    _handler.onChoose(currentMenu);
  }
  

}
