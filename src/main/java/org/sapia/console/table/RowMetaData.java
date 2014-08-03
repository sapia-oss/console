package org.sapia.console.table;


/**
 * Holds metadata about a {@link Row}.
 * 
 * @author Yanick Duchesne
 */
public class RowMetaData {
  
  private int columnCount;

  /**
   * @param colCount the number of columns that the new {@link RowMetaData} will holds.
   */
  RowMetaData(int colCount) {
    columnCount = colCount;
  }

  public int getColumnCount() {
    return columnCount;
  }
}
