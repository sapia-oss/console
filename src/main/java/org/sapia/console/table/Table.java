package org.sapia.console.table;

import org.sapia.console.ConsoleOutput;

/**
 * Generates tabular content. An instance of this class is composed of {@link Row}s that subdivided
 * in {@link Cell}s.
 * 
 * @author Yanick Duchesne
 *
 */
public class Table {
  private TableMetaData meta;
  private ConsoleOutput writer;

  /**
   * @param writer the {@link ConsoleOutput} to write to.
   * @param numCol the number of columns in the table.
   * @param colWidth the column with.
   */
  public Table(ConsoleOutput writer, int numCol, int colWidth) {
    this.writer = writer;
    meta        = new TableMetaData(numCol, colWidth);
  }

  /**
   * @return this instance's {@link TableMetaData}.
   */
  public TableMetaData getTableMetaData() {
    return meta;
  }

  /**
   * @return creates new {@link Row} associated to this instance and returns it.
   */
  public Row newRow() {
    Row r = new Row(this);

    return r;
  }

  /**
   * Draws a line out of repeating the given character by a number of times equivalent to <code>length</code>.
   * 
   * @param toDraw a character to repeat.
   * @param from the index at which to start.
   * @param length the length of the line to display, in numbers of characters.
   */
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

  /**
   * Draws a line out of repeating the given character up to this instance's width.
   * 
   * @param toDraw ta charactor to repeat.
   */
  public void drawLine(char toDraw) {
    int w = getTableMetaData().getTableWidth();

    for (int i = 0; i < w; i++) {
      getOutput().print(toDraw);
    }

    getOutput().println();
    getOutput().flush();
  }

  /**
   * @return this instance's {@link ConsoleOutput}
   */
  ConsoleOutput getOutput() {
    return writer;
  }
}
