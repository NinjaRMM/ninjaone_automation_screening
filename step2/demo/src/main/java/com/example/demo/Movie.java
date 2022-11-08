package com.example.demo;

import lombok.Data;

@Data
public class Movie {
    private String title;
    private int year;
    private String [] cast;
    private String [] genres;


}
