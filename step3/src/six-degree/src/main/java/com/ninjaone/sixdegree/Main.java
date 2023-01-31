package com.ninjaone.sixdegree;

import com.ninjaone.sixdegree.model.Movie;
import com.ninjaone.sixdegree.service.FileContentReader;
import com.ninjaone.sixdegree.service.impl.JsonFileContentReaderImpl;
import com.ninjaone.sixdegree.service.impl.SixDegreesService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner readData = new Scanner(System.in);
        System.out.println("Enter first person's name:");
        String person1 = readData.nextLine();

        readData = new Scanner(System.in);
        System.out.println("Enter second person's name:");
        String person2 = readData.nextLine();

        FileContentReader<Movie> contentReader = new JsonFileContentReaderImpl();
        List<Movie> movies = contentReader.readAll("../../../data/80s-movies.json");

        SixDegreesService sixDegreesService = new SixDegreesService();
        sixDegreesService.calculateDegree(person1, person2, movies);
    }
}