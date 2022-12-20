package com.step2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class FilterMovies {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a valid decade from 1900 to 2020 : ");
        int num = scanner.nextInt();
        System.out.println("You entered: " + num);

        // Parse the decade parameter
        String decade = ""+num;
        if(decade.length() == 4){
        } else if (decade.length() == 2) {
            decade = "19"+decade;
        } else {
            System.err.println("Invalid decade or year");
            System.exit(1);
        }

        int startYear = Integer.parseInt(decade);

        int endYear = startYear + 9;

        // Load the movies from the JSON file
        JSONArray movies = loadMovies();
        if (movies == null) {
            System.exit(1);
        }

        // Filter the movies by decade
        List<JSONObject> moviesInDecade = new ArrayList<JSONObject>();
        for (int i = 0; i < movies.length(); i++) {
            JSONObject movie = movies.getJSONObject(i);
            int year = movie.getInt("year");
            if (year >= startYear && year <= endYear) {
                moviesInDecade.add(movie);
            }
        }

        // Write the filtered movies to a new JSON file
        boolean success = writeMovies(moviesInDecade, decade + "s-movies.json");
        if (!success) {
            System.exit(1);
        }
    }

    static JSONArray loadMovies() {
        try {
            // Read the entire contents of the JSON file into a string
            StringBuilder jsonString = new StringBuilder();
            BufferedReader reader = new BufferedReader(new FileReader("./data/movies.json"));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            reader.close();

            // Parse the JSON string into a JSON array
            return new JSONArray(jsonString.toString());
        } catch (IOException e) {
            System.out.println("Error reading movies.json: " + e.getMessage());
            return null;
        }
    }

    private static boolean writeMovies(List<JSONObject> movies, String filename) {
        try {
            // Convert the list of movies to a JSON array
            JSONArray moviesArray = new JSONArray(movies);

            // Write the JSON array to a file
            FileWriter writer = new FileWriter("data/"+filename);
            writer.write(moviesArray.toString());
            writer.close();

            return true;
        } catch (IOException e) {
            System.out.println("Error writing to " + filename + ": " + e.getMessage());
            return false;
        }
    }
}