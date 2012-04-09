package org.sapia.console.table;

import org.sapia.console.ConsoleOutput;

/**
 * @author Yanick Duchesne
 *
 */
public class Table {
  private TableMetaData _meta;
  private ConsoleOutput _writer;

  public Table(ConsoleOutput writer, int numCol, int colWidth) {
    _writer   = writer;
    _meta     = new TableMetaData(numCol, colWidth);
  }

  public TableMetaData getTableMetaData() {
    return _meta;
  }

  public Row newRow() {
    Row r = new Row(this);

    return r;
  }

  public void drawLine(char toDraw, int from, int length) {
    for (int i = 0; i < from; i++) {
      getOutput().print(' ');
    }

    for (int i = 0; i < length; i++) {
      getOutput().print(toDraw);
    }

    getOutput().println();
    getOutput().flush();
  }

  public void drawLine(char toDraw) {
    int w = getTableMetaData().getTableWidth();

    for (int i = 0; i < w; i++) {
      getOutput().print(toDraw);
    }

    getOutput().println();
    getOutput().flush();
  }

  ConsoleOutput getOutput() {
    return _writer;
  }
}
