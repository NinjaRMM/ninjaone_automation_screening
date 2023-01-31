package com.ninjaone.sixdegree.service.impl;

import com.ninjaone.sixdegree.model.Movie;

import java.util.AbstractMap.SimpleEntry;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class SixDegreesService {

    private Map<String, List<Movie>> GRAPH = new HashMap<>();

    public void calculateDegree(String person1, String person2, List<Movie> movies) {
        if (person2 == null || person2.isBlank()) {
            person2 = "Kevin Bacon";
        }

        if (!mapMovies(person1, movies)) {
            System.out.println(person1 + " did not star in a movie in the data provided.");
            return;
        }

        if (!mapMovies(person2, movies)) {
            System.out.println(person2 + " did not star in a movie in the data provided.");
            return;
        }

        LinkedHashSet<String> visited = new LinkedHashSet<>();
        Map<String, List<String>> selected = new LinkedHashMap<>();
        selected.put(person1, new ArrayList<>());
        LinkedList<Entry<String, Integer>> queue = new LinkedList<>();
        queue.add(new SimpleEntry<>(person1, 0));

        while (!queue.isEmpty()) {
            Entry<String, Integer> currentEntry = queue.poll();

            if (currentEntry.getKey().equals(person2)) {
                printResult(selected.get(person2), person1, person2);
                break;
            }

            if (visited.contains(currentEntry.getKey())) continue;

            visited.add(currentEntry.getKey());

            if (!GRAPH.containsKey(currentEntry.getKey())) {
                mapMovies(currentEntry.getKey(), movies);
            }

            calculateCoWorkersDistance(visited, selected, queue, currentEntry);
        }
    }

    private void calculateCoWorkersDistance(LinkedHashSet<String> visited, Map<String, List<String>> selected, LinkedList<Entry<String, Integer>> queue, Entry<String, Integer> currentEntry) {
        Set<String> coWorkers = getCoWorkers(currentEntry.getKey());
        coWorkers.removeAll(visited);

        List<String> path = new ArrayList<>(selected.get(currentEntry.getKey()));
        path.add(currentEntry.getKey());

        coWorkers.forEach(c -> {
            if (selected.get(currentEntry.getKey()).size() > path.size() || !selected.containsKey(c)) {
                selected.put(c, path);
                queue.add(new SimpleEntry<>(c, path.size()));
            }
        });
    }

    private Set<String> getCoWorkers(String person) {
        return GRAPH.get(person).parallelStream().map(Movie::getCast).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    private boolean mapMovies(String person, List<Movie> movies) {
        List<Movie> filteredMovies = movies.parallelStream().filter(m -> m.getCast().contains(person)).collect(Collectors.toList());

        if (filteredMovies.isEmpty()) {
            return false;
        }

        GRAPH.put(person, filteredMovies);
        return true;
    }

    private void printResult(List<String> path, String person1, String person2) {
        System.out.println(String.format("There are %d degrees of separation between %s and %s", path.size(), person1, person2));
        path.add(person2);

        for (int i = 0; i < path.size(); i++) {
            for (int j = 0; j < GRAPH.get(path.get(i)).size(); j++) {
                Movie movie = GRAPH.get(path.get(i)).get(j);
                int nextRecord = i+1;
                if (nextRecord < path.size() && movie.getCast().contains(path.get(nextRecord))) {
                    System.out.println(path.get(i) + " starred with "+ path.get(nextRecord) + " in " + movie.getTitle());
                }
            }
        }
    }

}
