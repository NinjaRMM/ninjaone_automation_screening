package com.ninjaone.step3.cli;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ninjaone.step3.model.graph.CastGraph;
import com.ninjaone.step3.service.MovieService;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(name = "parse", mixinStandardHelpOptions = true)
public class ParseCommand implements Callable<Integer> {

    private static final String INPUT_FILE_PATH = "../data/%s";
    private static final String KEVIN_BACON = "Kevin Bacon";

    @Autowired
    private MovieService movieService;

    @Option(names = { "-a", "--actors" }, description = "One or two actor names, within single quotes, which degrees "
            + "of separation (among movies in the input file) you want to find out", arity = "1..2")
    private List<String> actors;

    @Option(names = { "-i", "--input" }, description = "The input file's name, that must be in the 'data' folder")
    private String inputFileName;

    @Override
    public Integer call() throws IOException {

        if (inputFileName == null || inputFileName.isEmpty()) {
            throw new IllegalArgumentException("Please provide the input file's name.");
        }

        if (actors == null || actors.isEmpty()) {
            throw new IllegalArgumentException("Please provide at least one actor's name.");
        } else if (actors.size() == 1) {
            actors.add(KEVIN_BACON);
        }

        CastGraph castGraph = new CastGraph(movieService.extractMoviesFromFile(
                new File(String.format(INPUT_FILE_PATH, inputFileName))));

        // degrees = actors in the "degree-chain" - "root actor" - "target actor", so it's size - 2
        Integer degrees = castGraph.getDegreesOfSeparation(actors.get(0), actors.get(1)).size() - 2;

        System.out.println("Output: " + degrees);

        return 0;
    }

}
