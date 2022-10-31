package com.ninjaone.model;

import lombok.Data;

import java.util.List;

@Data
public class Movies {

    private String title;
    private int year;
    private List<String> cast;
    private List<String> genres;
}
