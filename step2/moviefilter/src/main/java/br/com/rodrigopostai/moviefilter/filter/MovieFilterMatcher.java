package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;

import java.util.Optional;

public interface MovieFilterMatcher {
    boolean matches(Movie movie, MovieFilter filter);
    Optional<String> getFilterApplied(MovieFilter filter);
}
