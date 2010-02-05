package org.sapia.console.table;


/**
 * @author Yanick Duchesne
 * 2002-03-05
 *
 */
public class RowMetaData {
  private int _columnCount;

  /**
   * Constructor for RowMetaData.
   */
  RowMetaData(int colCount) {
    _columnCount = colCount;
  }

  public int getColumnCount() {
    return _columnCount;
  }
}
