package org.sapia.console.table;


/**
 * @author Yanick Duchesne
 * 2002-03-05
 *
 */
class Utils {
  static void formatLine(Line line, int cellWidth) {
    if (line._content.length() > cellWidth) {
      StringBuffer split = new StringBuffer();
      split.append(line._content.substring(cellWidth));
      line._content.delete(cellWidth, line._content.length());

      Line newLine = new Line();
      newLine._content   = new StringBuffer(split.toString().trim());
      line._next         = newLine;
      formatLine(newLine, cellWidth);
    } else {
      appendSpaces(line._content, cellWidth);
    }
  }

  private static void appendSpaces(StringBuffer content, int cellWidth) {
    for (int i = content.length(); i < cellWidth; i++) {
      content.append(' ');
    }
  }

  private static StringBuffer lTrim(StringBuffer content) {
    StringBuffer buf = new StringBuffer();
    int          i = 0;

    while ((content.charAt(i) == ' ') && (i < content.length())) {
      i++;
    }

    if (i < content.length()) {
      buf.append(content.substring(i));
    }

    return buf;
  }

  public static void main(String[] args) {
    Table t = new Table(System.out, 1, 80);
    t.drawLine('=');

    //		Table t = new Table(System.out, 2, 5);
    //		Row r = t.newRow();
    //		Cell c;
    //		r.getCellAt(0).append("Hello World Maximum ouch");
    //		c = r.getCellAt(1);
    //		t.getTableMetaData().getColumnMetaDataAt(1).setWidth(10);
    //		c.append("The food is in the barn");
    //
    //		r.flush();
    //	
    //		t.drawLine('=');
    //		
    //		r.flush();
    //    Line line = new Line();
    //    line._content.append("Hello World Maximum ouch");
    //    formatLine(line, 5);
    //    line.addEmptyLines(3, 5, 1);
    //    System.out.println(line.render(5, 1));
    //    System.out.println("" + line.getLineCount());    
  }
}
