package org.sapia.console.table;


/**
 * @author Yanick Duchesne
 * 2002-03-04
 *
 */
public class TableMetaData {
  private int              _colCount;
  private int              _colWidth;
  private int              _width;
  private ColumnMetaData[] _cols;

  TableMetaData(int colCount, int width) {
    _colCount   = colCount;
    _colWidth   = width;
    _cols       = new ColumnMetaData[colCount];

    for (int i = 0; i < colCount; i++) {
      _cols[i] = new ColumnMetaData(this, width);
    }

    calcWidth();
  }

  public int getColumnCount() {
    return _colCount;
  }

  public void setColWidth(int width) {
    _colWidth = width;
  }

  public ColumnMetaData getColumnMetaDataAt(int index) {
    return _cols[index];
  }

  public int getColWidth() {
    return _colWidth;
  }

  public int getTableWidth() {
    return _width;
  }

  void calcWidth() {
    int width = 0;

    for (int i = 0; i < _cols.length; i++) {
      width = width + _cols[i].getWidth() + (_cols[i].getCellPadding() * 2);
    }

    _width = width;
  }
}
