package step2.utils;


import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Handles parsing the expected command line arguments.
 */
public class CommandLineParser {
  private int decade;
  private String outputFile;
  private final Options options = new Options();

  /**
   * Parses the command line arguments.
   * @param args The arguments to read.
   * @throws ParseException If unable to parse the arguments.
   */
  public void parseInputs(String[] args) throws ParseException {
    var input = new Option("d", "decade", true, "input file path");
    input.setRequired(true);
    options.addOption(input);

    var output = new Option("o", "output", true, "output file");
    output.setRequired(true);
    options.addOption(output);

    var parser = new DefaultParser();
    var cmd = parser.parse(options, args);
    decade = Integer.parseInt(cmd.getOptionValue("decade"));
    outputFile = cmd.getOptionValue("output");
  }

  public void printHelp() {
    new HelpFormatter().printHelp("step2", options);
  }

  public int getDecade() {
    return decade;
  }

  public String getOutputFile() {
    return outputFile;
  }
}
