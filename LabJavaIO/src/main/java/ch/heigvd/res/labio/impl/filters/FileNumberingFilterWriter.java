package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 *
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

  private int numberOfLine = 1;
  private boolean r = false; // detection of \r before \n

  private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());

  public FileNumberingFilterWriter(Writer out) {
    super(out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    write(str.toCharArray(), off, len);// convertion of string into chararray
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = 0; i < len; ++i) {
      this.write(cbuf[off + i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    if (numberOfLine == 1) { //on first line, wee add the tabulation on the numberOfLine
      out.write(numberOfLine + "\t");
      ++numberOfLine;
    }
    if (r == true) { // if last caracter was a '\r', we check if there is a '\n'
      if ((char) c != '\n') {
        out.write(numberOfLine + "\t");
        ++numberOfLine;
      }
    }

    r = false;

    if ((char) c == '\r') {
      out.write('\r');
      r = true;
    } else {
      out.write((char) c);
      if (c == '\n') {
        out.write(numberOfLine + "\t");
        ++numberOfLine;
      }
    }
  }

}

