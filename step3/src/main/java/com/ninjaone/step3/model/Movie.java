package com.ninjaone.step3.model;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Movie {

    private String title;
    private Integer year;
    private Set<String> cast;
    private Set<String> genres;

}
