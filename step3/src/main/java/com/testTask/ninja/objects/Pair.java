package com.testTask.ninja.objects;

public class Pair {
    private final String actor1;
    private final String actor2;
    private final String movie;
    private final int year;

    public Pair(Actor actor1, Actor actor2, Movie movie) {
        this.actor1 = actor1.getName();
        this.actor2 = actor2.getName();
        this.movie = movie.getTitle();
        this.year = movie.getYear();
    }

    @Override
    public String toString() {
        return actor1.toUpperCase() + " played with " + actor2.toUpperCase() + " in movie '" + movie + "'(" + year + ")";
    }
}
