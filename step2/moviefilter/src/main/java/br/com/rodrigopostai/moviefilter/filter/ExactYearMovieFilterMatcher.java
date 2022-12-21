package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;

import java.util.Optional;

public class ExactYearMovieFilterMatcher implements MovieFilterMatcher {
    @Override
    public boolean matches(Movie movie, MovieFilter filter) {
        if (filter.getExactYear() != null) {
            return filter.getExactYear().equals(movie.getYear());
        }
        return true;
    }

    @Override
    public Optional<String> getFilterApplied(MovieFilter filter) {
        if (filter.getExactYear() != null) {
            return Optional.of(Integer.toString(filter.getExactYear()));
        }
        return Optional.empty();
    }
}
