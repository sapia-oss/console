package org.sapia.console.table;

/**
 * @author Yanick Duchesne
 */
class Utils {

  static void formatLine(Line line, int cellWidth) {
    if (line._content.length() > cellWidth) {
      StringBuffer split = new StringBuffer();
      split.append(line._content.substring(cellWidth));
      line._content.delete(cellWidth, line._content.length());

      Line newLine = new Line();
      newLine._content = new StringBuffer(split.toString().trim());
      line._next = newLine;
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

}
