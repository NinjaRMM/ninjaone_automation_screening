package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;

import java.util.Optional;

public class AfterYearMovieFilterMatcher implements MovieFilterMatcher {
    @Override
    public boolean matches(Movie movie, MovieFilter filter) {
        if (filter.getAfterYear() != null) {
            return movie.getYear() >= filter.getAfterYear();
        }
        return true;
    }

    @Override
    public Optional<String> getFilterApplied(MovieFilter filter) {
        if (filter.getAfterYear() != null) {
            return Optional.of(Integer.toString(filter.getAfterYear()));
        }
        return Optional.empty();
    }
}
