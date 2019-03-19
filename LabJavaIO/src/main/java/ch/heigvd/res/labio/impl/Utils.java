package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 *
 * @author Olivier Liechti
 */
public class Utils {

  private static final Logger LOG = Logger.getLogger(Utils.class.getName());

  /**
   * This method looks for the next new line separators (\r, \n, \r\n) to extract
   * the next line in the string passed in arguments.
   *
   * @param lines a string that may contain 0, 1 or more lines
   * @return an array with 2 elements; the first element is the next line with
   * the line separator, the second element is the remaining text. If the argument does not
   * contain any line separator, then the first element is an empty string.
   */
  public static String[] getNextLine(String lines) {
    String[] elements = new String[2];
    String[] newLineSeparators = {"\r", "\n", "\r\n"};
    int indexLine;

    if ((indexLine = lines.indexOf(newLineSeparators[2])) != -1) {
      elements[0] = lines.substring(0, indexLine + newLineSeparators[2].length());
      elements[1] = lines.substring(indexLine + newLineSeparators[2].length(), lines.length());
    } else if ((indexLine = lines.indexOf(newLineSeparators[1])) != -1) {
      elements[0] = lines.substring(0, indexLine + newLineSeparators[1].length());
      elements[1] = lines.substring(indexLine + newLineSeparators[1].length(), lines.length());
    } else if ((indexLine = lines.indexOf(newLineSeparators[0])) != -1) {
      elements[0] = lines.substring(0, indexLine + newLineSeparators[0].length());
      elements[1] = lines.substring(indexLine + newLineSeparators[0].length(), lines.length());
    } else {
      elements[0] = "";
      elements[1] = lines;
    }
    return elements;
  }

}
