package com.testTask.ninja;

import org.apache.commons.cli.*;

import java.util.List;

public class Main {
    private static int decadeValue;
    private static String outputFileName;

    public static void main(String[] args) {
        validateParams(args);
        List<Movie> movies = MovieService.readFileToList();
        List<Movie> moviesByDecade = MovieService.getMoviesByDecade(movies, decadeValue);
        MovieService.saveToFile(moviesByDecade, outputFileName);
    }

    private static void validateParams(String[] args){
        Options options = new Options();

        Option decade = new Option("d", "decade", true, "decade value(20-90)");
        decade.setRequired(true);
        options.addOption(decade);

        Option output = new Option("o", "output", true, "output file name(.json)");
        output.setRequired(true);
        options.addOption(output);

        try {
            CommandLine cmd = new DefaultParser().parse(options, args);
            decadeValue = Integer.parseInt(cmd.getOptionValue("decade"));
            outputFileName = cmd.getOptionValue("output");
        } catch (ParseException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
