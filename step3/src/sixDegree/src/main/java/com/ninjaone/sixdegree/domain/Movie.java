package com.ninjaone.sixdegree.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Movie {
    private String title;
    private String year;
    private String[] cast;
    private String[] genres;

}
