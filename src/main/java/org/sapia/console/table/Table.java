package org.sapia.console.table;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * @author Yanick Duchesne
 * 2002-03-04
 *
 */
public class Table {
  private TableMetaData _meta;
  private PrintWriter   _writer;

  public Table(Writer writer, int numCol, int colWidth) {
    _writer   = new PrintWriter(writer, true);
    _meta     = new TableMetaData(numCol, colWidth);
  }

  public Table(OutputStream out, int numCol, int colWidth) {
    _writer   = new PrintWriter(new OutputStreamWriter(out));
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
      getWriter().print(' ');
    }

    for (int i = 0; i < length; i++) {
      getWriter().print(toDraw);
    }

    getWriter().println();
    getWriter().flush();
  }

  public void drawLine(char toDraw) {
    int w = getTableMetaData().getTableWidth();

    for (int i = 0; i < w; i++) {
      getWriter().print(toDraw);
    }

    getWriter().println();
    getWriter().flush();
  }

  PrintWriter getWriter() {
    return _writer;
  }
}
