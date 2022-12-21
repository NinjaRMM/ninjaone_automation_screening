package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;

import java.util.Optional;

public class BeforeYearMovieFilterMatcher implements MovieFilterMatcher {
    @Override
    public boolean matches(Movie movie, MovieFilter filter) {
        if (filter.getBeforeYear() != null) {
            return movie.getYear() <= filter.getBeforeYear();
        }
        return true;
    }

    @Override
    public Optional<String> getFilterApplied(MovieFilter filter) {
        if (filter.getBeforeYear() != null) {
            return Optional.of(Integer.toString(filter.getBeforeYear()));
        }
        return Optional.empty();
    }
}
