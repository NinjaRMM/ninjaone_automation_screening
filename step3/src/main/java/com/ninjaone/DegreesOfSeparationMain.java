package com.ninjaone;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class DegreesOfSeparationMain {
    private static final String MOVIES_JSON_PATH = "../data/movies.json";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static List<MovieDegreeOfSeparation> movieList = new ArrayList<>();

    public static void main(String[] args) {
        if (args.length > 2)
            throw new RuntimeException("Please add 1 or 2 actor names as parameters.");
        String actorOne = args[0];
        String actorTwo = null;
        if (args.length > 1)
            actorTwo = args[1];

        movieList = readMovieListFromJsonFile();

        boolean isActorOneValid = false;
        boolean isActorTwoValid = false;
        if (actorTwo == null) {
            actorTwo = "Kevin Bacon";
            isActorTwoValid = true;
        } else {
            isActorTwoValid = checkIfActorIsValid(actorTwo);
        }
        isActorOneValid = checkIfActorIsValid(actorOne);

        if (!isActorOneValid) {
            System.out.println(actorOne + " did not star in a movie in the data provided");
            return;
        } else if (!isActorTwoValid) {
            System.out.println(actorTwo + " did not star in a movie in the data provided");
            return;
        }

        findDegreesOfSeparation(actorOne, actorTwo);
    }

    private static List<MovieDegreeOfSeparation> readMovieListFromJsonFile() {
        try {
            return objectMapper.readValue(new File(MOVIES_JSON_PATH),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, MovieDegreeOfSeparation.class));
        } catch (IOException e) {
            System.out.println("Error when trying to read the movies.json file: " + e.getMessage());
            throw new RuntimeException("Error when trying to read the movies.json file: " + e.getMessage());
        }
    }

    private static boolean checkIfActorIsValid(String actorName) {
        for (MovieDegreeOfSeparation movie : movieList) {
            for (String actor : movie.getCast()) {
                if (actor.equals(actorName)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static void findDegreesOfSeparation(String actorOne, String actorTwo) {
        Queue<String> actorsToProcess = new LinkedList<>();
        Map<String, Integer> actorToDistance = new HashMap<>();
        Map<String, String> actorToPreviousActor = new HashMap<>();
        Set<String> processedActors = new HashSet<>();

        actorsToProcess.add(actorOne);
        actorToDistance.put(actorOne, 0);

        while (!actorsToProcess.isEmpty()) {
            String currentActor = actorsToProcess.poll();
            int currentDistance = actorToDistance.get(currentActor);

            if (currentActor.equals(actorTwo)) {
                System.out.println("There are " + currentDistance + " degrees of separation between " + actorOne + " and " + actorTwo);
                String previousActor = actorTwo;
                for (int i = currentDistance; i > 0; i--) {
                    String movie = "";
                    for (MovieDegreeOfSeparation m : movieList) {
                        if (m.getCast().contains(previousActor) && m.getCast().contains(actorToPreviousActor.get(previousActor))) {
                            movie = m.getTitle();
                            break;
                        }
                    }
                    System.out.println(previousActor + " starred with " + actorToPreviousActor.get(previousActor) + " in " + movie);
                    previousActor = actorToPreviousActor.get(previousActor);
                }
                return;
            }

            for (MovieDegreeOfSeparation movie : movieList) {
                if (movie.getCast().contains(currentActor)) {
                    for (String anotherActor : movie.getCast()) {
                        if (!processedActors.contains(anotherActor)) {
                            actorsToProcess.add(anotherActor);
                            actorToDistance.put(anotherActor, currentDistance + 1);
                            actorToPreviousActor.put(anotherActor, currentActor);
                            processedActors.add(anotherActor);
                        }
                    }
                }
            }
        }
    }
}
