package io.automation.entities;

import lombok.Data;

import java.util.List;

@Data
public class Movie {

    private String title;
    private int year;
    private List<String> cast;
    private List<String> genres;
}
