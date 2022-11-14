package com.henriquebjr.movieshistory.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    private String title;
    private Integer year;
    private List<String> cast = new ArrayList<>();
    private List<String> genres = new ArrayList<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<String> getCast() {
        return cast;
    }

    public void setCast(List<String> cast) {
        this.cast = cast;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public static final class Builder {
        private Movie movie;

        private Builder() {
            movie = new Movie();
        }

        public static Builder of() {
            return new Builder();
        }

        public Builder title(String title) {
            movie.setTitle(title);
            return this;
        }

        public Builder year(Integer year) {
            movie.setYear(year);
            return this;
        }

        public Builder cast(List<String> cast) {
            movie.setCast(cast);
            return this;
        }

        public Builder genres(List<String> genres) {
            movie.setGenres(genres);
            return this;
        }

        public Movie build() {
            return movie;
        }
    }
}
