package org.sapia.console.table;


/**
 * @author Yanick Duchesne
 */
public class Row {
  private Table   _table;
  private Cell[]  _cells;
  private boolean _flushed;

  /**
   * Constructor for Row.
   */
  Row(Table owner) {
    _table   = owner;
    _cells   = new Cell[owner.getTableMetaData().getColumnCount()];

    for (int i = 0; i < _cells.length; i++) {
      _cells[i] = new Cell(this, i);
    }
  }

  public Table getTable() {
    return _table;
  }

  public Cell getCellAt(int index) {
    return _cells[index];
  }

  public int getCellCount() {
    return _cells.length;
  }

  public void flush() {
    if (_flushed) {
      return;
    }

    StringBuffer content = new StringBuffer();

    Cell         biggest = null;

    for (int i = 0; i < _cells.length; i++) {
      _cells[i].format();

      if (biggest == null) {
        biggest = _cells[i];
      } else if (_cells[i].getHeight() > biggest.getHeight()) {
        biggest = _cells[i];
      }
    }

    int h = biggest.getHeight();

    for (int i = 0; i < _cells.length; i++) {
      _cells[i].pack(h);
    }

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < _cells.length; j++) {
        String cell = _cells[j].getContent(i);
        content.append(cell);
      }

      if (i < (h - 1)) {
        content.append(System.getProperty("line.separator"));
      }
    }

    _table.getOutput().println(content.toString());
    _table.getOutput().flush();
    _flushed = true;
  }
}
