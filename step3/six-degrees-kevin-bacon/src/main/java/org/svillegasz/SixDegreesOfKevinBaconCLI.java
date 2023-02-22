package org.svillegasz;

import org.apache.commons.cli.*;
import org.svillegasz.exceptions.ActorNotFoundException;
import org.svillegasz.exceptions.ConnectionNotFoundException;

public class SixDegreesOfKevinBaconCLI {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("i", "input", true, "input file path");
        options.addOption("a1", "actor1", true, "first actor name");
        options.addOption("a2", "actor2", true, "second actor name (optional)");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            String moviesFilePath = cmd.getOptionValue("i", "movies.json");
            String actor2 = cmd.getOptionValue("a2", "Kevin Bacon");
            String actor1 = cmd.getOptionValue("a1");
            if (actor1 == null) throw new ParseException("Missing option value for the actor 1");

            SixDegreesOfKevinBacon sixDegrees = new SixDegreesOfKevinBacon(moviesFilePath);
            sixDegrees.findDegreesOfSeparation(actor1, actor2);
        } catch (ParseException e) {
            System.out.println("Error parsing command line options: " + e.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("SixDegreesOfKevinBaconCLI", options);
            System.exit(1);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
