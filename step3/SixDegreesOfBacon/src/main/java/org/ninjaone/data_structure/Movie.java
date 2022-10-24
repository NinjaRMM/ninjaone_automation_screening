package org.ninjaone.data_structure;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Movie {
    @Getter @Setter
    private String title;
    @Getter @Setter
    private List<String> cast;
}
