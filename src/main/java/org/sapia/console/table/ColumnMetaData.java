package org.sapia.console.table;


/**
 * Holds metadata about a column in a {@link Table}.
 * 
 * @author Yanick Duchesne
 */
public class ColumnMetaData {
  
  private int           width;
  private int           cellpadding = 1;
  private TableMetaData meta;

  /**
   * @param meta the {@link TableMetaData} corresponding to the table to which this instance belongs.
   * @param width the column's width.
   */
  ColumnMetaData(TableMetaData meta, int width) {
    this.meta  = meta;
    this.width = width;
  }

  /**
   * @param width a new width.
   */
  public void setWidth(int width) {
   this. width = width;
   meta.calcWidth();
  }
  
  /**
   * @param newWidth the new width to assign to this instance.
   * 
   * @see TableMetaData#adjustToNewWidth(int)
   */
  void ajustToNewWidth(int newWidth) {
    this.width = newWidth;
  }

  /**
   * @return this instance's width.
   */
  public int getWidth() {
    return width;
  }

  /**
   * @return this instance's cell padding.
   */
  public int getCellPadding() {
    return cellpadding;
  }

  /**
   * @param spacing a new cellpadding value.
   */
  public void setCellPadding(int spacing) {
    if (spacing <= 0) {
      cellpadding = 1;
    } else {
      cellpadding = spacing;
    }

    meta.calcWidth();
  }
}
