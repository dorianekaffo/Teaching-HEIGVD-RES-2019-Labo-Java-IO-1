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
    //converting the string into a char array to use offset and a length
    write(str.toCharArray(), off, len);
  }

  @Override
  public void write(char[] cbuf, int off, int len) throws IOException {
    for (int i = 0; i < len; ++i) {
      this.write(cbuf[off + i]);
    }
  }

  @Override
  public void write(int c) throws IOException {
    // If we are at the first line, we add the tabin the number of line
    if (numberOfLine == 1) {
      out.write(numberOfLine + "\t");
      ++numberOfLine;
    }
    // If the last character was a '\r', we look if we have a '\n'
    if (r == true) {
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

