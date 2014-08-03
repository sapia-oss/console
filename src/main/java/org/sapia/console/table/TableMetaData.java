package org.sapia.console.table;


/**
 * Holds the metadata for a {@link Table}.
 * 
 * @author Yanick Duchesne
 *
 */
public class TableMetaData {
  private int              colCount;
  private int              width;
  private ColumnMetaData[] columns;

  TableMetaData(int colCount, int width) {
    this.colCount = colCount;
    columns       = new ColumnMetaData[colCount];

    for (int i = 0; i < colCount; i++) {
      columns[i] = new ColumnMetaData(this, width);
    }

    calcWidth();
  }

  /**
   * @return the number of columns in this instance's corresponding {@link Table}.
   */
  public int getColumnCount() {
    return colCount;
  }
  
  /**
   * @param index the index of the desired {@link ColumnMetaData}.
   * @return the {@link ColumnMetaData} at the given index.
   */
  public ColumnMetaData getColumnMetaDataAt(int index) {
    return columns[index];
  }

  /**
   * @return the total with of this table.
   */
  public int getTableWidth() {
    return width;
  }
  /**
   * Set's this instance's new width. Internally adjusts the width of each column according to the
   * ratio of this instance's current width vs the new width.
   * 
   * @param newWidth a new width.
   */
  public void adjustToNewWidth(int newWidth) {
    if (newWidth != width) {
      int calculatedWith = 0;
  
      float expandRatio = (float) newWidth / (float) this.width;
      for (int i = 0; i < columns.length; i++) {
        columns[i].ajustToNewWidth((int) ((float) (columns[i].getWidth() * expandRatio)));
        calculatedWith = calculatedWith + columns[i].getWidth() + (columns[i].getCellPadding() * 2);
      }
      
      this.width = calculatedWith;
    }
  }

  void calcWidth() {
    int calculatedWith = 0;

    for (int i = 0; i < columns.length; i++) {
      calculatedWith = calculatedWith + columns[i].getWidth() + (columns[i].getCellPadding() * 2);
    }

    this.width = calculatedWith;
  }
}
