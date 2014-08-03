package org.sapia.console.table;

import org.sapia.console.ConsoleOutput;

/**
 * Corresponds to a row in a {@link Table}.
 * 
 * @author Yanick Duchesne
 */
public class Row {
  
  private Table   table;
  private Cell[]  cells;
  private boolean flushed;

  /**
   * @param owner the {@link Table} to which this instance belows.
   */
  Row(Table owner) {
    table   = owner;
    cells  = new Cell[owner.getTableMetaData().getColumnCount()];

    for (int i = 0; i < cells.length; i++) {
      cells[i] = new Cell(this, i);
    }
  }

  /**
   * @return {@link Table} to which this instance belongs.
   */
  public Table getTable() {
    return table;
  }

  /**
   * @param index the index whose corresponding {@link Cell} is desired.
   * @return the {@link Cell} at the given index.
   */
  public Cell getCellAt(int index) {
    return cells[index];
  }

  /**
   * @return the number of cells that this instance holds.
   */
  public int getCellCount() {
    return cells.length;
  }

  /**
   * Flushes this instance's content to its {@link ConsoleOutput}.
   * @see Table#getOutput()
   */
  public void flush() {
    if (flushed) {
      return;
    }

    StringBuffer content = new StringBuffer();

    Cell         biggest = null;

    for (int i = 0; i < cells.length; i++) {
      cells[i].format();

      if (biggest == null) {
        biggest = cells[i];
      } else if (cells[i].getHeight() > biggest.getHeight()) {
        biggest = cells[i];
      }
    }

    int h = biggest.getHeight();

    for (int i = 0; i < cells.length; i++) {
      cells[i].pack(h);
    }

    for (int i = 0; i < h; i++) {
      for (int j = 0; j < cells.length; j++) {
        String cell = cells[j].getContent(i);
        content.append(cell);
      }

      if (i < (h - 1)) {
        content.append(System.getProperty("line.separator"));
      }
    }

    table.getOutput().println(content.toString());
    table.getOutput().flush();
    flushed = true;
  }
}
