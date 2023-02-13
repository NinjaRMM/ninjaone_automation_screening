package com.ninjaone;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie {
    @JsonProperty("title")
    private String title;
    @JsonProperty("year")
    private int year;
    @JsonProperty("cast")
    private String[] cast;
    @JsonProperty("genres")
    private String[] genres;

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

    public String[] getCast() {
        return cast;
    }

    public void setCast(String[] cast) {
        this.cast = cast;
    }

    public String[] getGenres() {
        return genres;
    }

    public void setGenres(String[] genres) {
        this.genres = genres;
    }
}
