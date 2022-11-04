package com.testTask.ninja.objects;

import java.util.List;
import java.util.Objects;

public class Movie {
    private String title;
    private Integer year;
    private List<String> cast;
    private List<String> genres;

    public String getTitle() {
        return title;
    }

    public Integer getYear() {
        return year;
    }

    public List<String> getCast() {
        return cast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return Objects.equals(title, movie.title) && Objects.equals(year, movie.year) && Objects.equals(cast, movie.cast) && Objects.equals(genres, movie.genres);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, year, cast, genres);
    }
}

