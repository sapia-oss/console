package org.sapia.console.table;


/**
 * @author Yanick Duchesne
 * 2002-03-05
 *
 */
public class ColumnMetaData {
  private int           _width;
  private int           _cellPadding = 1;
  private TableMetaData _meta;

  /**
   * Constructor for ColumnMetaData.
   */
  ColumnMetaData(TableMetaData meta, int width) {
    _meta    = meta;
    _width   = width;
  }

  public void setWidth(int width) {
    _width = width;
    _meta.calcWidth();
  }

  public int getWidth() {
    return _width;
  }

  public int getCellPadding() {
    return _cellPadding;
  }

  public void setCellPadding(int spacing) {
    if (spacing <= 0) {
      _cellPadding = 1;
    } else {
      _cellPadding = spacing;
    }

    _meta.calcWidth();
  }
}
