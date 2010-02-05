package org.sapia.console.table;


/**
 * @author Yanick Duchesne
 * 2002-03-04
 *
 */
public class Cell {
  private Row  _row;
  private int  _index;
  private Line _content = new Line();

  /**
   * Constructor for Column.
   */
  Cell(Row owner, int index) {
    _row     = owner;
    _index   = index;
  }

  Row getRow() {
    return _row;
  }

  public int getIndex() {
    return _index;
  }

  public int getWidth() {
    return _row.getTable().getTableMetaData().getColumnMetaDataAt(_index)
               .getWidth();
  }

  public Cell append(String content) {
    _content.append(content);

    return this;
  }

  int getHeight() {
    return _content.getLineCount();
  }

  void format() {
    ColumnMetaData meta = _row.getTable().getTableMetaData()
                              .getColumnMetaDataAt(_index);
    _content.format(meta.getWidth());
  }

  void pack(int height) {
    ColumnMetaData meta = _row.getTable().getTableMetaData()
                              .getColumnMetaDataAt(_index);
    _content.addEmptyLines(height - _content.getLineCount(), meta.getWidth(),
      meta.getCellPadding());
  }

  String getContent(int lineNum) {
    ColumnMetaData meta = _row.getTable().getTableMetaData()
                              .getColumnMetaDataAt(_index);
    Line           line = _content.getLineAt(lineNum);

    //_content.addEmptyLines(height - _content.getLineCount(), meta.getWidth(), meta.getCellSpacing());				
    return line.render(meta.getWidth(), meta.getCellPadding());
  }
}
