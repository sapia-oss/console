package org.sapia.console.widgets;


/**
 * Handles the selection of a given {@link MenuOption}
 * 
 * @author yduchesne
 *
 */
public interface MenuOptionHandler {
  
  /**
   * 
   * @param currentMenu the {@link Menu} containing the option
   * that was selected.
   */
  public void onChoose(Menu currentMenu);

}
