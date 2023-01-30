package com.ninjaone.filtermovies.model;

import lombok.Data;

import java.util.List;

@Data
public class Movie {
    private String title;
    private Integer year;
    private List<String> cast;
    private List<String> genres;
}
