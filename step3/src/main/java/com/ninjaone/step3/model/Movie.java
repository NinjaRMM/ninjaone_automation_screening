package com.ninjaone.step3.model;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

    private String title;
    private Integer year;
    private Set<String> cast;
    private Set<String> genres;

}
