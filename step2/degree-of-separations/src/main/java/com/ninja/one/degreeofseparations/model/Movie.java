package com.ninja.one.degreeofseparations.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Movie {
    private String title;
    private Integer year;
    private List<String> cast;
    private List<String> genres;
}
