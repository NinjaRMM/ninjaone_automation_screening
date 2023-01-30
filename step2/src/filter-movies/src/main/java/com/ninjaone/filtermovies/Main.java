package com.ninjaone.filtermovies;

import com.google.gson.Gson;
import com.ninjaone.filtermovies.model.Movie;
import com.ninjaone.filtermovies.service.FileContentReader;
import com.ninjaone.filtermovies.service.FilterService;
import com.ninjaone.filtermovies.service.impl.JsonFileContentReaderImpl;
import com.ninjaone.filtermovies.service.impl.MovieFilterServiceImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner readData = new Scanner(System.in);
        System.out.println("Enter a valid decade [0, 10, 20, ..., 90] :");
        int decade = Integer.valueOf(readData.nextLine());

        FileContentReader<Movie> contentReader = new JsonFileContentReaderImpl();
        FilterService filterService = new MovieFilterServiceImpl();

        List<Movie> movies = contentReader.readAll("../../../data/movies.json");
        movies = filterService.filter(decade, movies);

        saveResult(movies, decade);
    }

    private static void saveResult(List<Movie> movies, int decade) throws IOException {
        Gson gson = new Gson();

        try (FileWriter writer = new FileWriter("../../../data/" + decade + "s-movies.json")) {
            gson.toJson(movies, writer);
            System.out.println("Results saved successfully");
        }
    }
}