package br.com.rodrigopostai.moviefilter.filter;

import br.com.rodrigopostai.moviefilter.domain.Movie;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

public class StringContainsMovieFilterMatcher implements MovieFilterMatcher {
    @Override
    public boolean matches(Movie movie, MovieFilter filter) {
        if (StringUtils.isNotBlank(filter.getTitleContains())) {
            return StringUtils.containsIgnoreCase(movie.getTitle(), filter.getTitleContains());
        }
        return true;
    }

    @Override
    public Optional<String> getFilterApplied(MovieFilter filter) {
        if (StringUtils.isNotBlank(filter.getTitleContains())) {
            return Optional.of(filter.getTitleContains());
        }
        return Optional.empty();
    }
}
