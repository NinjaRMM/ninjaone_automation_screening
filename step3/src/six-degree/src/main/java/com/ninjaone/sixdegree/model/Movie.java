package com.ninjaone.sixdegree.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Movie {
    private String title;
    private Integer year;
    private List<String> cast;
    private List<String> genres;
}
