package com.ninjaone;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Movie {
    @JsonProperty("title")
    private String title;
    @JsonProperty("year")
    private int year;
    @JsonProperty("cast")
    private List<String> cast;
    @JsonProperty("genres")
    private List<String> genres;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
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
}
