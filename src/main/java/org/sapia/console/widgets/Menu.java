package org.sapia.console.widgets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sapia.console.Console;

/**
 * A menu encapsulates {@link MenuOption}s. Usage:
 * 
 * <pre>
 * Menu menu = console.menu("Choose an option:");
 * 
 * menu.option("Choice 1", 
 *  new MenuOptionHandler(){
 *    public void onChoose(Menu current){
 *      current.create("Sub Menu 1").display();
 *    }
 *  });
 *  
 * menu.display();
 * </pre>
 * 
 * Same result as above, in a more DSL fashion:
 * 
 * <pre>
 * console.menu("Choose an option:")
 *   .option("Choice 1", 
 *      new MenuOptionHandler(){
 *        public void onChoose(Menu current){
 *          current.create("Sub Menu 1").display();
 *        }
 *    })
 *   .display();   
 * </pre>
 * 
 * @author yduchesne
 *
 */
public class Menu {

  private Menu _parent;
  private String _title;
  private Console _console;
  private List<MenuOption> _options = new ArrayList<MenuOption>();
  
  public Menu(Console console){
    _console = console;
  }
  
  public Menu(String title, Console console){
    this(console);
    _title = title;
  }  

  /**
   * Internally creates a {@link MenuOption} and adds it to this instance.
   * @param label the option's label.
   * @param handler the {@link MenuOptionHandler} that is to be associated to the option.
   * @return
   */
  public Menu option(String label, MenuOptionHandler handler){
    MenuOption option =  new MenuOption(_options.size()+1, label, handler);
    _options.add(option);
    return this;
  }
  
  /**
   * Creates a new {@link Menu} with this instance has its parent.
   * 
   * @param title the title to assign to the new menu.
   * @return the new {@link Menu}.
   * 
   * @see #back()
   */
  public Menu create(String title){
    Menu child = new Menu(title, _console);
    child.setParent(this);
    return child;
  }

  /**
   * @return the {@link Console} associated to this instance.
   */
  public Console console(){
    return _console;
  }
  
  /**
   * Displays this menu to its console's output and waits for user input.
   */
  public void display(){
    if(_title != null){
      _console.println();
      _console.println(_title);
      _console.println();
    }
    for(MenuOption opt:_options){
      opt.display(_console);
    }
    try{
      String input = _console.input("");
      if(input == null || input.length() == 0){
        back();
      }
      else{
        try{
          int index = Integer.parseInt(input) - 1;
          if(index < 0 || index >= _options.size()){
            back();
          }
          else{
            _options.get(index).onChoose(this);
          }
        }catch(NumberFormatException e){
          back();
        }
      }
    }catch(IOException e){
      throw new IllegalStateException("Could not get input");
    }
  }
  
  /**
   * Displays this menu's parent, if any, or simply returns.
   */
  public void back(){
    if(_parent != null){
      _parent.display();
    }
    else{
      return;
    }    
  }
  
  void setParent(Menu parent){
    _parent = parent;
  }
  
}
