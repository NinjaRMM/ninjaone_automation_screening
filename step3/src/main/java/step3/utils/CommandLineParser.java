package step3.utils;


import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Handles parsing the expected command line arguments.
 */
public class CommandLineParser {
  private String nameOne;
  private String nameTwo;

  /**
   * Parses the command line arguments.
   * @param args The arguments to read.
   * @throws ParseException If unable to parse the arguments.
   */
  public void parseInputs(String[] args) throws ParseException {
    nameOne = args.length > 0 ? args[0] : null;
    nameTwo = args.length > 1 ? args[1] : null;
  }

  public String getNameOne() {
    return nameOne;
  }

  public String getNameTwo() {
    return nameTwo;
  }
}
