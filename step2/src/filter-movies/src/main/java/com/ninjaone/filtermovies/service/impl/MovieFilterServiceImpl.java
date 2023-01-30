package com.ninjaone.filtermovies.service.impl;

import com.ninjaone.filtermovies.model.Movie;
import com.ninjaone.filtermovies.service.FilterService;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class MovieFilterServiceImpl implements FilterService<Movie, Integer> {
    @Override
    public List<Movie> filter(Integer decade, List<Movie> movies) {
        if (movies == null) {
            return null;
        }

        checkDecade(decade);

        return movies
                .parallelStream()
                .filter(m -> (m.getYear() % 100) >= decade && (m.getYear() % 100) <= decade+9)
                .collect(toList());
    }

    private void checkDecade(Integer decade) {
        if (decade == null || decade % 10 != 0 || decade > 90) {
            throw new RuntimeException("The value of the decade must range from 0 to 90 and must be a multiple of 10");
        }
    }
}
